package com.askerlve.knowledge.threadlocal;

import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @author Askerlve
 * @Description: TODO
 * @date 2018/7/27上午10:07
 */
public class ThreadLocalTest {

    private static boolean myValueFinalized;
    private static boolean threadLocalUserFinalized;
    private static boolean myThreadLocalFinalized;
    private static boolean myThreadFinalized;

    public void setUp() {
        myValueFinalized = false;
        threadLocalUserFinalized = false;
        myThreadLocalFinalized = false;
        myThreadFinalized = false;
    }

    public static void setMyValueFinalized() {
        myValueFinalized = true;
    }

    public static void setThreadLocalUserFinalized() {
        threadLocalUserFinalized = true;
    }

    public static void setMyThreadLocalFinalized() {
        myThreadLocalFinalized = true;
    }

    public static void setMyThreadFinalized() {
        myThreadFinalized = true;
    }

    private void collectGarbage() {
        for (int i = 0; i < 10; i++) {
            System.gc();
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    @Test
    public void test1() {
        ThreadLocalUser user = new ThreadLocalUser();
        MyValue value = new MyValue(1);
        user.setThreadLocal(value);
        user.clear();
        value = null;
        collectGarbage();
        Assert.assertTrue(myValueFinalized);
        Assert.assertFalse(threadLocalUserFinalized);
        Assert.assertFalse(myThreadLocalFinalized);
    }

    // weird case
    @Test
    public void test2() {
        ThreadLocalUser user = new ThreadLocalUser();
        MyValue value = new MyValue(1);
        user.setThreadLocal(value);
        value = null;
        user = null;
        collectGarbage();
        Assert.assertFalse(myValueFinalized);
        Assert.assertTrue(threadLocalUserFinalized);
        //垃圾回收时会清理
        Assert.assertTrue(myThreadLocalFinalized);
    }

    /**
     * ThreadLocalUser.finalize 0
     * MyValue.finalize 1
     * MyThreadLocal.finalize
     */
    @Test
    public void test3() throws InterruptedException {
        Thread t = new MyThread(() -> {
            ThreadLocalUser user = new ThreadLocalUser();
            MyValue value = new MyValue(1);
            user.setThreadLocal(value);
        });
        t.start();
        t.join();
        collectGarbage();
        Assert.assertTrue(myValueFinalized);
        Assert.assertTrue(threadLocalUserFinalized);
        Assert.assertTrue(myThreadLocalFinalized);
        Assert.assertFalse(myThreadFinalized);
    }

    @Test
    public void test4() throws InterruptedException {
        Executor singlePool = Executors.newSingleThreadExecutor();
        singlePool.execute(() -> {
            ThreadLocalUser user = new ThreadLocalUser();
            MyValue value = new MyValue(1);
            user.setThreadLocal(value);
        });
        Thread.sleep(100);
        collectGarbage();
        Assert.assertFalse(myValueFinalized);
        Assert.assertTrue(threadLocalUserFinalized);
        Assert.assertTrue(myThreadLocalFinalized);
    }

    @Test
    public void test5() throws Exception {
        for (int i = 0; i < 100; i++) {
            ThreadLocalUser user = new ThreadLocalUser(i);
            MyValue value = new MyValue(i);
            user.setThreadLocal(value);
            value = null;
            user = null;
        }
        collectGarbage();
        Assert.assertFalse(myValueFinalized);
        Assert.assertTrue(threadLocalUserFinalized);
        Assert.assertTrue(myThreadLocalFinalized);
    }

    @Test
    public void test6() throws Exception {
        for (int i = 0; i < 100; i++) {
            ThreadLocalUser user = new ThreadLocalUser(i);
            MyValue value = new MyValue(i);
            user.setThreadLocal(value);
            value = null;
            user = null;
            collectGarbage();
        }

        Assert.assertTrue(myValueFinalized);
        Assert.assertTrue(threadLocalUserFinalized);
        Assert.assertTrue(myThreadLocalFinalized);
    }

}

class MyThread extends Thread {

    public MyThread(Runnable target) {
        super(target);
    }
    @Override
    protected void finalize() throws Throwable {
        System.out.println("MyThread.finalize");
        ThreadLocalTest.setMyThreadFinalized();
        super.finalize();
    }

}

class MyValue {

    private final int value;

    public MyValue(int value) {
        this.value = value;
    }

    @Override
    protected void finalize() throws Throwable {
        System.out.println("MyValue.finalize " + value);
        ThreadLocalTest.setMyValueFinalized();
        super.finalize();
    }

}

class MyThreadLocal<T> extends ThreadLocal<T> {

    @Override
    protected void finalize() throws Throwable {
        System.out.println("MyThreadLocal.finalize");
        ThreadLocalTest.setMyThreadLocalFinalized();
        super.finalize();
    }

}

class ThreadLocalUser {

    private final int num;
    private MyThreadLocal<MyValue> value = new MyThreadLocal<>();

    public ThreadLocalUser() {
        this(0);
    }

    public ThreadLocalUser(int num) {
        this.num = num;
    }

    @Override
    protected void finalize() throws Throwable {
        System.out.println("ThreadLocalUser.finalize " + num);
        ThreadLocalTest.setThreadLocalUserFinalized();
        super.finalize();
    }

    public void setThreadLocal(MyValue myValue) {
        value.set(myValue);
    }

    public void clear() {
        value.remove();
    }

}

