package com.askerlve.knowledge.jmh.test2;

/**
 * @author Askerlve
 * @Description: 串行计算
 * @date 2018/6/13上午10:48
 */
public class SinglethreadCalculator implements Calculator {
    @Override
    public long sum(int[] numbers) {
        long total = 0L;
        for (int i : numbers) {
            total += i;
        }
        return total;
    }

    @Override
    public void shutdown() {

    }
}
