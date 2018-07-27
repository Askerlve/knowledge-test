package com.askerlve.knowledge.stream;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 * @author Askerlve
 * @Description: TODO
 * @date 2018/7/19下午5:03
 */
public class StreamTest1 {

    List<String> stringCollection = new ArrayList<>();

    {
        stringCollection.add("ddd2");
        stringCollection.add("aaa2");
        stringCollection.add("bbb1");
        stringCollection.add("aaa1");
        stringCollection.add("bbb3");
        stringCollection.add("ccc");
        stringCollection.add("bbb2");
        stringCollection.add("ddd1");
    }

    @Test
    public void test1(){



        // filtering
        stringCollection
                .stream()
                .filter((s) -> s.startsWith("a"))
                .forEach(System.out::println);
        // "aaa2", "aaa1"

        // sorting
        stringCollection
                .stream()
                .sorted()
                .filter((s) -> s.startsWith("a"))
                .forEach(System.out::println);
        // "aaa1", "aaa2"


        // mapping
        stringCollection
                .stream()
                .map(String::toUpperCase)
                .sorted(Comparator.reverseOrder())
                .forEach(System.out::println);
        // "DDD2", "DDD1", "CCC", "BBB3", "BBB2", "AAA2", "AAA1"


        // matching
        boolean anyStartsWithA = stringCollection
                .stream()
                .anyMatch((s) -> s.startsWith("a"));
        System.out.println(anyStartsWithA);      // true

        boolean allStartsWithA = stringCollection
                .stream()
                .allMatch((s) -> s.startsWith("a"));
        System.out.println(allStartsWithA);      // false

        boolean noneStartsWithZ = stringCollection
                .stream()
                .noneMatch((s) -> s.startsWith("z"));
        System.out.println(noneStartsWithZ);      // true

        // counting
        long startsWithB = stringCollection
                .stream()
                .filter((s) -> s.startsWith("b"))
                .count();
        System.out.println(startsWithB);    // 3


        // reducing
        Optional<String> reduced =
                stringCollection
                        .stream()
                        .sorted()
                        .reduce((s1, s2) -> s1 + "#" + s2);
        reduced.ifPresent(System.out::println);
        // "aaa1#aaa2#bbb1#bbb2#bbb3#ccc#ddd1#ddd2"


    }
}
