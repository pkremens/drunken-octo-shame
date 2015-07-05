package org.jboss.qe.jdk8.annotations;

import com.sun.istack.internal.NotNull;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @author Petr Kremensky pkremens@redhat.com on 03/07/2015
 */
@Version(major = 1)

public class Code {

    public void print(@NotNull String toBePrinted) {
        System.out.println(toBePrinted);
    }

    @Version(major = 2, minor = 3)
    public Code() {
        System.out.println("Constructor");
    }

    @Version(major = 2)
    @ChangeLog(date = "today", comments = "in train")
    @ChangeLog(date = "tommorrow", comments = "home")
    public String toString() {
        return "Code";
    }

    public static void main(String[] args) throws NoSuchMethodException {
        Class<Code> code = Code.class;
        System.out.println("Print class annotations");
        Arrays.stream(code.getAnnotations()).forEach(System.out::println);
        System.out.println("Print methods annotations");
        Arrays.stream(code.getMethods()).forEach(method -> Arrays.stream(method.getAnnotations()).forEach(System.out::println));
        System.out.println("Print package annotations");
        Arrays.stream(code.getPackage().getAnnotations()).forEach(System.out::println);
        System.out.println("Print major and minor version for toString method");
        Method method = code.getMethod("toString");
        System.out.println("major = " + method.getAnnotation(Version.class).major());
        System.out.println("minor = " + method.getAnnotation(Version.class).minor());

        Class<ChangeLog> annClass = ChangeLog.class;

        // Access annotations using the ChangeLog type
        System.out.println("\nUsing the ChangeLog type...");
        ChangeLog[] annList = method.getAnnotationsByType(ChangeLog.class);
        for (ChangeLog log : annList) {
            System.out.println("Date=" + log.date() +
                    ", Comments=" + log.comments());
        }

        // Access annotations using the ChangeLogs containing annotation type
        System.out.println("\nUsing the ChangeLogs type...");
        Class<ChangeLogs> containingAnnClass = ChangeLogs.class;
        ChangeLogs logs = method.getAnnotation(containingAnnClass);
        for (ChangeLog log : logs.value()) {
            System.out.println("Date=" + log.date() +
                    ", Comments=" + log.comments());
        }
    }
}
