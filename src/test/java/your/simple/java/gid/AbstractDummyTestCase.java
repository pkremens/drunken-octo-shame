/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2014, Red Hat, Inc., and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

package your.simple.java.gid;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Random;

/**
 * @author <a href="mailto:pkremens@redhat.com">Petr Kremensky</a>
 */
public abstract class AbstractDummyTestCase {
    public static int random;
    public static int failrate;

    @BeforeClass
    public static void getRandom() {
        random = new Random().nextInt(Integer.MAX_VALUE);
    }

    @Test
    public void test1() {
        testRandom(1);
    }

    @Test
    public void test2() {
        testRandom(2);
    }

    @Test
    public void test3() {
        testRandom(3);
    }

    @Test
    public void test4() {
        testRandom(4);
    }

    @Test
    public void test5() {
        testRandom(5);
    }

    @Test
    public void test6() {
        testRandom(6);
    }

    @Test
    public void test7() {
        testRandom(7);
    }

    @Test
    public void test8() {
        testRandom(8);
    }

    @Test
    public void test9() {
        testRandom(9);
    }

    public void testRandom(int divider) {
        int result = random % divider;
        System.out.println(String.format("Random: %d, divider: %d, result=%d", random, divider, result));
        Assert.assertFalse(String.format("Failrate: %d, result=%d", failrate, result), result > failrate);
    }
}
