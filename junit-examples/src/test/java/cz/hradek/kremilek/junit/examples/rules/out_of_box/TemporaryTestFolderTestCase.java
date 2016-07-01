package cz.hradek.kremilek.junit.examples.rules.out_of_box;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.IOException;
import java.util.Arrays;

/**
 * http://meri-stuff.blogspot.cz/2014/08/junit-rules.html
 * <p>
 * Example Rule - Temporary Folder
 * Temporary folder rule creates new empty folder, runs test or test case and then deletes the folder. You can either
 * specify where to create the new folder, or let it be created in system temporary file directory.
 * <p>
 * Temporary folder can be used as both test rule and class rule.
 *
 * @author Petr Kremensky pkremens@redhat.com
 */
public class TemporaryTestFolderTestCase {
    // cannot use non-static variable in static content, @Rule cannot be static, see @RuleClasss
//    private static Boolean staticBlock = null;
//    {
//        staticBlock = folder.getRoot().exists();
//    }

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();


    @Before
    public void before() throws IOException {
        folder.newFile("before");
    }

    @After
    public void after() throws IOException {
        folder.newFile("after");
        System.out.println();
        Arrays.stream(folder.getRoot().list()).forEach(System.out::println);
    }

    @Test
    public void testMethod1() throws IOException {
        folder.newFile("testMethod1");
    }

    @Test
    public void testMethod2() throws IOException {
        folder.newFile("testMethod2");
    }
}
