package com.urise.WorkWithStreams;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Streams {

    public static void main(String args[]) {
        int[] array = new int[]{1, 2, 3, 3, 2, 3};
        int[] array2 = new int[]{9, 8};

        System.out.println(minValue(array));
        System.out.println(minValue(array2));

        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);

        System.out.println(oddOrEven(list));
    }

    private static int minValue(int[] values) {
        return Arrays.stream(values).sorted().distinct().reduce(0, (a, b) -> 10 * a + b);
    }

    private static List<Integer> oddOrEven(List<Integer> integers) {
        return integers.stream()
                .filter(n -> n % 2 != integers.stream().mapToInt(Integer::intValue)
                        .sum() % 2)
                .collect(Collectors.toList());
    }
}
