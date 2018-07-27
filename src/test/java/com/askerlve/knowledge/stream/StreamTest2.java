package com.askerlve.knowledge.stream;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author Askerlve
 * @Description: TODO
 * @date 2018/7/19下午5:17
 */
public class StreamTest2 {

    public static final int MAX = 1000000;


    public static void sortSequential() {
        List<String> values = new ArrayList<>(MAX);
        for (int i = 0; i < MAX; i++) {
            UUID uuid = UUID.randomUUID();
            values.add(uuid.toString());
        }

        // sequential
        long t0 = System.nanoTime();
        long count = values.stream().count();
        System.out.println(count);
        long t1 = System.nanoTime();
        long millis = TimeUnit.NANOSECONDS.toMillis(t1 - t0);
        System.out.println(String.format("sequential sort took: %d ms", millis));
    }

    public static void sortParallel() {
        List<String> values = new ArrayList<>(MAX);
        for (int i = 0; i < MAX; i++) {
            UUID uuid = UUID.randomUUID();
            values.add(uuid.toString());
        }

        // sequential
        long t0 = System.nanoTime();
        long count = values.parallelStream().count();
        System.out.println(count);

        long t1 = System.nanoTime();
        long millis = TimeUnit.NANOSECONDS.toMillis(t1 - t0);
        System.out.println(String.format("parallel sort took: %d ms", millis));
    }
}
