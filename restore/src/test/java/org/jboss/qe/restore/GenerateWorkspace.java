package org.jboss.qe.restore;

import org.apache.commons.io.FileUtils;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 * @author Petr Kremensky pkremens@redhat.com
 */
public class GenerateWorkspace extends TemporaryFolder {
    public File DELETE_ME;
    public File MODIFY_ME;
    public File ADD_ME;
    public File IGNORE_ME;

    public File DIRECTORY;
    public File FILE_IN_DIRECTORY;

    /**
     * We need some directories and text files we can modify in tests.
     */
    private void generateRubbish() throws IOException {

        DELETE_ME = newFile("delete");
        MODIFY_ME = newFile("modify");
        ADD_ME = new File(getRoot(), "add");
        IGNORE_ME = newFile("ignore");

        FileUtils.writeStringToFile(DELETE_ME, "This file is destined to be deleted.", Charset.defaultCharset());
        FileUtils.writeStringToFile(MODIFY_ME, "This file is destined to be modified.", Charset.defaultCharset());
        FileUtils.writeStringToFile(IGNORE_ME, "This file is destined to be ignored.", Charset.defaultCharset());

        DIRECTORY = newFolder("dir1", "dir2", "dir3");
        FILE_IN_DIRECTORY = new File(DIRECTORY, "inner");
        FileUtils.writeStringToFile(FILE_IN_DIRECTORY, "This file is destined to be inside directory structure.",
                Charset.defaultCharset());
    }

    @Override
    protected void before() throws Throwable {
        create();
        generateRubbish();
    }


}
