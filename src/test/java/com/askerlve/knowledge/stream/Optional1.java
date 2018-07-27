package com.askerlve.knowledge.stream;

import org.junit.Assert;
import org.junit.Test;

import java.util.Optional;
import java.util.function.Supplier;

/**
 * @author Askerlve
 * @Description: TODO
 * @date 2018/7/19下午3:44
 */
public class Optional1 {

    @Test
    public void test1() {
        Optional<String> optional = Optional.of("bam");
        Assert.assertTrue(optional.isPresent());         // true
        optional.get();                 // "bam"
        System.out.println(optional.orElse("fallback"));   // "bam"

        optional.ifPresent((s) -> System.out.println(s.charAt(0)));     // "b"
    }

    @Test
    public void test2() {
        Outer outer = new Outer();
        resolve(() -> outer.getNested().getInner().getFoo())
                .ifPresent(System.out::println);
    }

    @Test
    public void test3() {
        Optional.of(new Outer())
                .map(Outer::getNested)
                .map(Nested::getInner)
                .map(Inner::getFoo)
                .ifPresent(System.out::println);
    }

    @Test
    public void test4() {
        Optional.of(new Outer())
                .flatMap(o -> Optional.ofNullable(o.nested))
                .flatMap(n -> Optional.ofNullable(n.inner))
                .flatMap(i -> Optional.ofNullable(i.foo))
                .ifPresent(System.out::println);
    }

    public <T> Optional<T> resolve(Supplier<T> resolver) {
        try {
            T result = resolver.get();
            return Optional.ofNullable(result);
        }
        catch (NullPointerException e) {
            return Optional.empty();
        }
    }

    static class Outer {
        Nested nested = new Nested();

        public Nested getNested() {
            return nested;
        }
    }

    static class Nested {
        Inner inner = new Inner();

        public Inner getInner() {
            return inner;
        }
    }

    static class Inner {
        String foo = "boo";

        public String getFoo() {
            return foo;
        }
    }

}
