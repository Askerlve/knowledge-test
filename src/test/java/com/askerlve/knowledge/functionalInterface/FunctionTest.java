package com.askerlve.knowledge.functionalInterface;

import org.junit.Test;

import java.util.function.Function;

/**
 * @author Askerlve
 * @Description: TODO
 * @date 2018/7/12下午2:54
 */
public class FunctionTest {

    @Test
    public void testApply(){
        FunctionTest functionTest = new FunctionTest();
        Function<Integer, Integer> func = e -> e + 5;
        int result = functionTest.calculate(5, func);
        System.out.println(result);
    }

    public int calculate(Integer a, Function<Integer, Integer> function) {
        return function.apply(a);
    }

    @Test
    public void testAndThen(){
        Function<Integer, Integer> func = e -> e + 5;
        Function<Integer, Integer> func2 = e -> e * 5;
        System.out.println(func.andThen(func2).apply(5));
    }

    @Test
    public void testCompose(){
        Function<Integer, Integer> func = e -> e + 5;
        Function<Integer, Integer> func2 = e -> e * 5;
        System.out.println(func.compose(func2).apply(5));
    }

}
