package com.askerlve.knowledge.jmh.test2;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * @author Askerlve
 * @Description: CalculateBenchmark
 * @date 2018/6/13上午10:46
 */
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@State(Scope.Benchmark)
public class CalculateBenchmark {

    /**
     * @Param 可以用来指定某项参数的多种情况。特别适合用来测试一个函数在不同的参数输入的情况下的性能。
     */
    @Param({"10000", "100000", "1000000"})
    private int length;

    private int[] numbers;
    private Calculator singleThreadCalc;
    private Calculator multiThreadCalc;

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(CalculateBenchmark.class.getSimpleName())
                .forks(2)
                .warmupIterations(5)
                .measurementIterations(5)
                .build();

        new Runner(opt).run();
    }

    @Benchmark
    @Group("test2")
    public long singleThreadBench() {
        return singleThreadCalc.sum(numbers);
    }

    @Benchmark
    @Group("test2")
    public long multiThreadBench() {
        return multiThreadCalc.sum(numbers);
    }

    /**
     * @Setup 会在执行 benchmark 之前被执行，正如其名，主要用于初始化。
     */
    @Setup
    public void prepare() {
        numbers = IntStream.rangeClosed(1, length).toArray();
        singleThreadCalc = new SinglethreadCalculator();
        multiThreadCalc = new MultithreadCalculator(Runtime.getRuntime().availableProcessors());
    }

    /**
     * @TearDown 和 @Setup 相对的，会在所有 benchmark 执行结束以后执行，主要用于资源的回收等。
     */
    @TearDown
    public void shutdown() {
        singleThreadCalc.shutdown();
        multiThreadCalc.shutdown();
    }

}
