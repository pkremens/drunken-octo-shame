package org.jboss.qe.restore;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 * Basic unit tests for {@link Restore} class. All public methods should be tested. Create extra test case class for
 * more advanced use cases.
 *
 * @author Petr Kremensky pkremens@redhat.com
 */
public class RestoreTestCase {

    private Restore restore;

    // generate bollocks before every test
    @Rule
    public GenerateWorkspace workspace = new GenerateWorkspace();
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void initStageAllTest() throws RestoreException {
        restore = new Restore(workspace.getRoot());
    }

    @Test
    public void initStageAllExplicitlyTest() throws RestoreException {
        String[] filenames = workspace.getRoot().list();
        restore = new Restore(workspace.getRoot(), filenames);
    }

    @Test
    @Ignore
    public void initNoExceptionOnNonexistingIncludeTest() throws RestoreException {
        restore = new Restore(workspace.getRoot(), "foo", "bar");
        // all files should be ignored now
//        Arrays.stream(workspace.getRoot().list((dir, name) -> !name.startsWith(".git"))).forEach(System.out::println);
//        Arrays.stream(workspace.getRoot().list(this::gitFileFilter);
    }

    // TBD Adam bien jak to bylo s funkcnima interface v lambdach sakra
    @Test
    @Ignore
    public void exceptionOnNonExistingWorkspaceTest() throws RestoreException {
        File nonExisting = new File(System.getProperty("java.io.tmpdir"),
                String.valueOf(System.currentTimeMillis()));
        if (nonExisting.exists()) {
            throw new RuntimeException("Cannot perform the test, file should not exist");
        }
        thrown.expect(RestoreException.class);
        restore = new Restore(nonExisting);

    }

    @Test
    @Ignore
    public void initIgnoreFileTest() {

    }

    @Test
    @Ignore
    public void initIgnoreDirTest() {

    }

    @Test
    @Ignore
    public void initIgnoreSubDirTest() {

    }

    @Test
    @Ignore
    public void initExistingRepoTest() {

    }

    @Test
    @Ignore
    public void statusTest() {

    }

    @Test
    @Ignore
    public void diffTest() {

    }

    /*
     * Make sure that wipeout() remove extra files.
     */
    @Test
    public void wipeoutCleanTest() throws RestoreException, RestoreDestroyedException, IOException {
        restore = new Restore(workspace.getRoot());
        Assert.assertTrue(restore.getStatus().isClean());

        Assert.assertFalse(workspace.ADD_ME.exists());
        workspace.ADD_ME.createNewFile();
        Assert.assertFalse(restore.getStatus().isClean());
        Assert.assertTrue(workspace.ADD_ME.exists());

        restore.wipeout();
        Assert.assertFalse(workspace.ADD_ME.exists());
        Assert.assertTrue(restore.getStatus().isClean());
    }

    @Test
    @Ignore
    public void wipeoutResetTest() {

    }

    /*
     * Make sure wipeout() can restore file to its original value.
     */
    @Test
    public void wipeoutCheckoutTest() throws RestoreException, RestoreDestroyedException, IOException {
        restore = new Restore(workspace.getRoot());
        Assert.assertTrue(restore.getStatus().isClean());
        final String original = FileUtils.readFileToString(workspace.MODIFY_ME, Charset.defaultCharset());

        FileUtils.writeStringToFile(workspace.MODIFY_ME, "Wipeout me!", Charset.defaultCharset());
        Assert.assertFalse(restore.getStatus().isClean());
        Assert.assertNotEquals(original, FileUtils.readFileToString(workspace.MODIFY_ME, Charset.defaultCharset()));

        restore.wipeout();
        Assert.assertTrue(restore.getStatus().isClean());
        Assert.assertEquals(original, FileUtils.readFileToString(workspace.MODIFY_ME, Charset.defaultCharset()));
    }

    @Test
    @Ignore
    public void destroyTest() {

    }

    @Test
    @Ignore
    public void toStringTest() {
    }
}
