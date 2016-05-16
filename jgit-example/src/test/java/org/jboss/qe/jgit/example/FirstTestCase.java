package org.jboss.qe.jgit.example;

import org.apache.commons.io.FileUtils;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.ResetCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * http://www.nuxeo.com/blog/jgit-example/
 *
 * @author Petr Kremensky pkremens@redhat.com on 16/05/2016
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FirstTestCase {
    //    private static final File WORKSPACE = new File("/home/pkremens/workspace/repo");
    private static final File WORKSPACE = new File("target/repo");
    private static Git git;
    private static RevCommit rev;
    private static Repository repo;

    @BeforeClass
    public static void clearRepo() {
        FileUtils.deleteQuietly(WORKSPACE);
        assertTrue(WORKSPACE.mkdir());
    }


    @Test
    public void aCreateRepo() throws IOException {
        // Create a Repository object
        repo = FileRepositoryBuilder.create(new File(WORKSPACE, ".git"));
        repo.create();
        git = new Git(repo);
    }

    @Test
    public void bCommnit() throws GitAPIException, IOException {
        // Create a new file and add it to the index
        for (int i = 0; i < 3; i++) {
            File newFile = new File(WORKSPACE, "myNewFile" + i);
            newFile.createNewFile();
            git.add().addFilepattern(newFile.getName()).call();
        }
        // Now, we do the commit with a message
        rev = git.commit().setAuthor("pkremens", "pkremens@redhat.com").setMessage("Init").call();
    }

    @Test
    public void cMakeChanges() throws GitAPIException, IOException {
        assertTrue(git.status().call().isClean());
        new File(WORKSPACE, "myNewFile5").createNewFile();
        new File(WORKSPACE, "myNewFile0").delete();
        FileUtils.writeStringToFile(new File(WORKSPACE, "myNewFile1"), "hello");
        assertFalse(git.status().call().isClean());
    }

    @Test
    public void dWipeoutChanges() throws GitAPIException {
        /*
         * [pkremens@dhcp-10-40-4-117 repo] (master)$ ls
         * myNewFile0  myNewFile1  myNewFile2
         */
        git.diff().call().forEach(diffEntry -> System.out.println(diffEntry.toString()));

        git.reset().setMode(ResetCommand.ResetType.HARD).setRef("HEAD").call();
        git.checkout().setAllPaths(true).call();
        git.clean().setCleanDirectories(true).call().forEach(System.out::println);
        assertTrue(git.status().call().isClean());
    }

}
