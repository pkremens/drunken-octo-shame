import org.jboss.qe.restore.Restore;
import org.jboss.qe.restore.RestoreException;

import java.io.File;
import java.io.IOException;

/**
 * @author Petr Kremensky pkremens@redhat.com on 29/06/2016
 */
public class Demo {
    public static void main(String[] args) throws RestoreException, IOException {
        Restore restore = new Restore(new File("/home/pkremens/workspace3"), "undeploy.scr", "test");
        new File("/home/pkremens/workspace3/asdqwezxc").createNewFile();
        new File("/home/pkremens/workspace3/undeploy.scr").delete();
        System.out.println(restore);
        restore.wipeout();
        System.out.println(restore);
        restore.destroy();
        System.out.println(restore);
    }
}
