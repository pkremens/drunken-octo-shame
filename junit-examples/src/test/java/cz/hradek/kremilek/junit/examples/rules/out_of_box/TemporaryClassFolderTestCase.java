package cz.hradek.kremilek.junit.examples.rules.out_of_box;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
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
public class TemporaryClassFolderTestCase {
    {
        try {
            Thread.sleep(2L);
            folder.newFile(System.currentTimeMillis() + " static");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @ClassRule
    public static TemporaryFolder folder = new TemporaryFolder();


    @BeforeClass
    public static void beforeClass() throws IOException, InterruptedException {
        Thread.sleep(2L);
        folder.newFile(System.currentTimeMillis() + " before class");
    }

    @AfterClass
    public static void afterClass() throws IOException, InterruptedException {
        Thread.sleep(2L);
        folder.newFile(System.currentTimeMillis() + " after class");
        System.out.println();
        Arrays.stream(folder.getRoot().list()).sorted().forEach(System.out::println);
    }

    @Before
    public void before() throws IOException, InterruptedException {
        Thread.sleep(2L);
        folder.newFile(System.currentTimeMillis() + " before");
    }

    @After
    public void after() throws IOException, InterruptedException {
        Thread.sleep(2L);
        folder.newFile(System.currentTimeMillis() + " after");
    }

    @Test
    public void testMethod1() throws IOException, InterruptedException {
        Thread.sleep(2L);
        folder.newFile(System.currentTimeMillis() + " testMethod1");
    }

    @Test
    public void testMethod2() throws IOException, InterruptedException {
        Thread.sleep(2L);
        folder.newFile(System.currentTimeMillis() + " testMethod2");
    }


}
