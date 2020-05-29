package org.jboss.qe.jdk8.reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * @author Petr Kremensky pkremens@redhat.com on 06/07/2015
 */
public class Demo {
    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException {
        Class<String> string = String.class;
        System.out.println(string.getName());
        Class string2 = Class.forName("java.lang.String");
        System.out.println(string2.getName());
        string2 = "hello".getClass();
        System.out.println(string2.getName());

        System.out.println(string.getModifiers() & Modifier.classModifiers());
        /*
        Compare the binary representations of each of those.

    110 &     010 =     010
   1010 &    0101 =    0000
  10100 &   11001 =   10000
1111011 & 0010100 = 0010000
         */
        System.out.println(5 & 4);
        System.out.println(Modifier.toString(17));

        // Bypassing a private modifier on method or field
        Method setName = Person.class.getDeclaredMethod("setName", String.class);
        setName.setAccessible(true);
        Constructor<Person> constructor = Person.class.getConstructor(int.class, String.class);
        Person p = constructor.newInstance(1, "Petr");
        System.out.println(p.getName());
        setName.invoke(p, "Jan");
        System.out.println(p.getName());
        ExecutableUtil.getParameters(setName).forEach(System.out::println);

        System.out.println();
        SecurityManager securityMgr = System.getSecurityManager();
        if (securityMgr == null) {
            System.out.println("Security manager is not installed");
        }
    }
}
