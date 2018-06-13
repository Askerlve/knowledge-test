package com.askerlve.knowledge.jmh;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;

/**
 * @author Askerlve
 * @Description: JMHtest,
 * @date 2018/6/13上午10:14
 */

/**
 * (1).Throughput: 整体吞吐量，例如“1秒内可以执行多少次调用”。
 * (2).AverageTime: 调用的平均时间，例如“每次调用平均耗时xxx毫秒”。
 * (3).SampleTime: 随机取样，最后输出取样结果的分布，例如“99%的调用在xxx毫秒以内，99.99%的调用在xxx毫秒以内”
 * (4).SingleShotTime: 以上模式都是默认一次 iteration 是 1s，唯有 SingleShotTime 是只运行一次。往往同时把 warmup 次数设为0，用于测试冷启动时的性能。
 */
@BenchmarkMode(Mode.All)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@State(Scope.Thread)
public class JMHFirstBenchmark {

    @Benchmark
    public int sleepAWhile() {
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            // ignore
        }
        return 0;
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                /**
                 * benchmark 所在的类的名字
                 */
                .include(JMHFirstBenchmark.class.getSimpleName())
                /**
                 * 进行 fork 的次数。如果 fork 数是2的话，则 JMH 会 fork 出两个进程来进行测试
                 */
                .forks(1)
                /**
                 * 预热的迭代次数
                 */
                .warmupIterations(3)
                /**
                 * 实际测量的迭代次数
                 */
                .measurementIterations(3)
                .build();

        new Runner(opt).run();
    }

}
