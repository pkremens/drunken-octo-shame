package org.jboss.qe.jdk8.lambda;

import java.util.Arrays;

/**
 * @author Petr Kremensky pkremens@redhat.com on 23/06/2015
 */
public class Demo1 {

    static Integer valueOf(int i) {
        return new Integer(i);
    }

    public static void main(String[] args) {
//        int[] values = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
//        Integer[] values = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

        String[] values = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
//        int sum = Arrays.asList(values).stream().flatMapToInt(Integer::valueOf).sum();
        int sum = Arrays.asList(values).stream()
                .mapToInt(Integer::valueOf)
                .sum();
        System.out.println(sum);
// List<Integer> list = new ArrayList<Integer>();
//        list = Arrays.asList(values);
//        list.addAll(Arrays.asList(values));
//        System.out.println(sum);
//        Arrays.stream(values)
//                .boxed()
//                .collect(Collectors.toList());
    }


}
