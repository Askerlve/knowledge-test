package com.askerlve.knowledge.jmh;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;

/**
 * @author Askerlve
 * @Description: state test
 * @date 2018/6/13上午10:29
 */

/**
 * State 用于声明某个类是一个“状态”，然后接受一个 Scope 参数用来表示该状态的共享范围。因为很多 benchmark 会需要一些表示状态的类，JMH 允许你把这些类以依赖注入的方式注入到 benchmark 函数里,Scope 主要分为两种。
 * Thread: 该状态为每个线程独享。
 * Benchmark: 该状态在所有线程间共享。
 */
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@State(Scope.Benchmark)
public class JMHState {

    @State(Scope.Benchmark)
    public static class BenchmarkState {
        volatile double x = Math.PI;
    }

    @State(Scope.Thread)
    public static class ThreadState {
        volatile double x = Math.PI;
    }

    @Benchmark
    public void measureUnshared(ThreadState state) {
        state.x++;
    }

    @Benchmark
    public void measureShared(BenchmarkState state) {
        state.x++;
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(JMHState.class.getSimpleName())
                .warmupIterations(5)
                .measurementIterations(5)
                .threads(2)
                .forks(1)
                .build();

        new Runner(opt).run();
    }

}
