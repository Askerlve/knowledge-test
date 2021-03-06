package com.askerlve.knowledge.contented;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * @author Askerlve
 * @Description: XX:-RestrictContended
 * @date 2018/4/26下午6:23
 */
public class ContendedTest {

    byte a;
    @sun.misc.Contended("a")
    long b;
    @sun.misc.Contended("a")
    long c;
    int d;

    private static Unsafe UNSAFE;

    static {
        try {
            Field f = Unsafe.class.getDeclaredField("theUnsafe");
            f.setAccessible(true);
            UNSAFE = (Unsafe) f.get(null);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws NoSuchFieldException {
        System.out.println("offset-a: " + UNSAFE.objectFieldOffset(ContendedTest.class.getDeclaredField("a")));
        System.out.println("offset-b: " + UNSAFE.objectFieldOffset(ContendedTest.class.getDeclaredField("b")));
        System.out.println("offset-c: " + UNSAFE.objectFieldOffset(ContendedTest.class.getDeclaredField("c")));
        System.out.println("offset-d: " + UNSAFE.objectFieldOffset(ContendedTest.class.getDeclaredField("d")));

        ContendedTest contendedTest = new ContendedTest();

    }

}
