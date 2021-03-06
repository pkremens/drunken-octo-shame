package org.jboss.qe.restore;

import org.apache.commons.io.FileUtils;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.ResetCommand;
import org.eclipse.jgit.api.Status;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;

import java.io.File;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Set;

/**
 * Class designed to save and restore files in target directory.
 * <p>
 * User of the class can define a directory which should be managed by the {@link Restore} (optionally choose only
 * subset of files inside the target directory - the rest of the files in the directory will be completely ignored,
 * thus cannot be restored by {@link Restore}). {@link Restore} allows you to show all changes and restore
 * the content of the files to its original state.
 * <p>
 * Future plan:
 * Push &#38; Pop methods:
 * Push (create a new commit i (where i=0++)) would basically allow user to commit workspace changes in
 * {@code BeforeClass} method, wipeout to pushed version {@code Before} every test. Pop method would move HEAD one
 * commit back (where repo init commit is maximum where we can pop to). Pop method would take boolean parameter allowing
 * directly pop back to init commit (will be used in {@code destroy()} method).
 *
 * @author Petr Kremensky pkremens@redhat.com
 */
public class Restore {

    private Git git;
    private final String[] managedFiles;

    /**
     * Create a new {@link Restore} instance. Manage all files inside target {@code directory}.
     *
     * @param directory the {@link File} managed by {@link Restore}
     * @throws RestoreException in case we fail to initialize the restore object. Restore will try to delete all files
     *                          created during initialization (.git, .gitignore).
     */
    public Restore(File directory) throws RestoreException {
        this(directory, directory.list());
    }

    /**
     * Create a new {@link Restore} instance. Manage only files listed as {@code include} inside
     * the {@code directory}. Ignore all other files in the {@code directory}. If file is ignored, it will not be
     * restored to it's original state by calling neither {@link #wipeout()}, nor {@link #destroy()}. Deleted ignored
     * files will be lost as well.
     *
     * @param directory the {@link File} managed by {@link Restore}
     * @param include   list of files inside the {@code directory} which will be managed by Restore. The rest of
     *                  the files will be completely ignored (cannot be restored).
     * @throws RestoreException in case we fail to initialize the restore object. Restore will try to delete all files
     *                          created during initialization (.git, .gitignore).
     */
    public Restore(final File directory, final String... include) throws RestoreException {
        managedFiles = include;
        final File gitDir = new File(directory, ".git");
        final File gitIgnore = new File(directory, ".gitignore");

        try {
            // repo.create throws an exception in case .git exists
            FileUtils.deleteDirectory(gitDir);
            // noinspection ResultOfMethodCallIgnored
            gitIgnore.delete();
            Repository repo = FileRepositoryBuilder.create(gitDir);
            repo.create();
            git = new Git(repo);

            // add include files
            for (String untracked : include) {
                git.add().addFilepattern(untracked).call();
            }

            // list all untracked files and generate .gitIgnore file
            StringBuilder ignore = new StringBuilder();
            for (String untracked : git.status().call().getUntracked()) {
                ignore.append(untracked);
                if (new File(directory, untracked).isDirectory()) {
                    // add wildcard for directory
                    ignore.append(File.pathSeparator).append("*");
                }
                ignore.append(System.lineSeparator());
            }
            if (ignore.length() > 0) {
                FileUtils.writeStringToFile(gitIgnore, ignore.toString(), Charset.defaultCharset());
                git.add().addFilepattern(gitIgnore.getName()).call();
            }

            // commit
            String user = System.getProperty("user.name");
            git.commit().setAuthor(user, user + "@redhat.com").setMessage("Restore - init").call();

            // assert clean status now
            if (!git.status().call().isClean()) {
                throw new RestoreException("Failed to initialize a repository. Status should be clean at this point:\n"
                        + toString());
            }
        } catch (Exception e) {
            if (gitDir.exists()) {
                FileUtils.deleteQuietly(gitDir);
                FileUtils.deleteQuietly(gitIgnore);
            }
            throw new RestoreException(e.getMessage(), e);
        }
    }

    /**
     * Get the {@link Status} object representing the current state of managed files. {@link Status} can be used to
     * reprieve all changes in managed files.
     *
     * @return {@link Status} object representing the current state of managed files
     * @throws RestoreException          if we fail to get the status of managed directory
     * @throws RestoreDestroyedException current Restore instance was already destroyed by invoking {@link #destroy()}
     */
    public Status getStatus() throws RestoreException, RestoreDestroyedException {
        testDestroyed();
        try {
            return git.status().call();
        } catch (Exception e) {
            throw new RestoreException("Failed to get the status.", e);
        }
    }

    /**
     * Get the {@code List<DiffEntry>} of all differences among managed files.
     *
     * @return list of differences
     * @throws RestoreException          in case diff retrieval fails
     * @throws RestoreDestroyedException current Restore instance was already destroyed by invoking {@link #destroy()}
     */
    public List<DiffEntry> getDiff() throws RestoreException, RestoreDestroyedException {
        testDestroyed();
        try {
            return git.diff().call();
        } catch (GitAPIException e) {
            throw new RestoreException("Failed to get the diff for managed files.", e);
        }
    }

    /**
     * Revert the state of the managed directory into its original state.
     *
     * @throws RestoreException          if we fail to revert the state of the managed directory
     * @throws RestoreDestroyedException current Restore instance was already destroyed by invoking {@link #destroy()}
     */
    public void wipeout() throws RestoreException, RestoreDestroyedException {
        testDestroyed();
        // reset, checkout, and clean
        try {
            git.reset().setMode(ResetCommand.ResetType.HARD).setRef("HEAD").call();
            git.checkout().setAllPaths(true).call();
            git.clean().setCleanDirectories(true).call();

        } catch (Exception e) {
            throw new RestoreException("Failed to wipeout the managed directory.", e);
        }
        if (!getStatus().isClean()) {
            throw new RestoreException("Failed to wipeout a repository. Status should be clean at this point:\n"
                    + toString());
        }
    }

    /**
     * Restore the managed files into original state (call {@link #wipeout()} and remove all {@link Restore}
     * control files (.git, .gitignore).
     * <p>
     * WARNING!!!
     * This is a one time action, current {@code Restore} instance will be crippled by this invocation.
     *
     * @return {@code true} in case all that managed files were restored and all operating files were deleted.
     * @throws RestoreException          if we fail to revert the state of the managed directory and destroy all control
     *                                   files
     * @throws RestoreDestroyedException current Restore instance was already destroyed by invoking {@link #destroy()}
     */
    public boolean destroy() throws RestoreException, RestoreDestroyedException {
        testDestroyed();
        File gitDir = getGitDir();
        try {
            wipeout();
            git.getRepository().close();
            FileUtils.deleteDirectory(gitDir);
            git = null;
        } catch (Exception e) {
            throw new RestoreException("Failed to destroy the Restore.", e);
        }
        boolean ignoreDeleted = new File(gitDir.getParent(), ".gitignore").delete();
        return !ignoreDeleted || gitDir.exists();
    }

    /**
     * Throw {@link RestoreDestroyedException} in case that current instance was destroyed by {@link #destroy()}.
     */
    private void testDestroyed() throws RestoreDestroyedException {
        if (git == null) {
            throw new RestoreDestroyedException();
        }
    }

    private File getGitDir() {
        return git.getRepository().getDirectory();
    }

    /**
     * Get the information about managed directory in human readable form.
     *
     * @return a {@link String} representation of the object.
     */
    @Override
    public String toString() {
        if (git == null) {
            return "This Restore instance was already destroyed and cannot be reused. " +
                    "A new Restore instance have to be initialized";
        }

        StringBuilder sb = new StringBuilder();
        // managed directory details
        final File repo = git.getRepository().getDirectory().getParentFile();
        sb.append("Managed directory: ").append(System.lineSeparator());
        sb.append(repo.getAbsolutePath());
        sb.append(System.lineSeparator());
        for (String managed : managedFiles) {
            sb.append("|- ").append(managed).append(System.lineSeparator());
        }
        sb.append(System.lineSeparator());

        // status details
        Status status;
        try {
            status = git.status().call();
            sb.append("Status: ");
            sb.append(System.lineSeparator());
            if (status.isClean()) {
                sb.append("CLEAN");
            } else {
                addToStatus(sb, status.getUntracked(), "ADDED"); // untracked in git terms
                addToStatus(sb, status.getModified(), "MODIFIED");
                addToStatus(sb, status.getMissing(), "DELETED"); // removed in git terms
                return sb.toString();
            }
        } catch (GitAPIException e) {
            sb.append("Failed to get the repository status.");
        }
        return sb.toString();
    }

    private void addToStatus(StringBuilder sb, Set<String> files, String status) {
        if (files.size() > 0) {
            sb.append(status).append(System.lineSeparator());
            files.forEach(file ->
                    sb.append(" - ").append(file).append(System.lineSeparator())
            );
        }
    }
}
