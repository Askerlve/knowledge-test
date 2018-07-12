package com.askerlve.knowledge.functionalInterface;

import org.junit.Test;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.ToIntBiFunction;

/**
 * @author Askerlve
 * @Description: TODO
 * @date 2018/7/12下午3:49
 */
public class BiFunctionTest {

    @Test
    public void testBiFunction() {
        //定义BiFunction对象，并调用apply方法
        BiFunction<Integer, String, Integer> biFunction = (t, u) -> t + Integer.parseInt(u);
        System.out.println(biFunction.apply(5, "6"));

        //方法调用时，作为参数（操作）传入
        int result = this.compute(5, 6, (t, u) -> t + u);
        System.out.println(result); //11

        //BiFunction与Function的组合
        int result2 = this.compute2(5, 6, (t, u) ->  t + u, t -> t * t);
        System.out.println(result2); //121

        ToIntBiFunction<Integer,String> integerStringToIntBiFunction = (t,u) -> t + Integer.valueOf(u);

        System.out.println(integerStringToIntBiFunction.applyAsInt(5,"6"));
    }

    public int compute(Integer a, Integer b, BiFunction<Integer, Integer, Integer> biFunction) {
        return biFunction.apply(a, b);
    }

    //BIFunction的返回作为Function的输入
    public int compute2(Integer a, Integer b, BiFunction<Integer, Integer, Integer> biFunction, Function<Integer, Integer> function) {
        return biFunction.andThen(function).apply(a, b);
    }
}
