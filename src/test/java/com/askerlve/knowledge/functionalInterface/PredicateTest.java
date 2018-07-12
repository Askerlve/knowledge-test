package com.askerlve.knowledge.functionalInterface;

import com.alibaba.fastjson.JSON;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author Askerlve
 * @Description: TODO
 * @date 2018/7/12下午4:57
 */
public class PredicateTest {


    private List<Person> personList = Arrays.asList(
            new Person(21,"zhangsan"),
            new Person(22,"lisi"),
            new Person(23,"wangwu"),
            new Person(24,"wangwu"),
            new Person(25,"lisi"),
            new Person(26,"zhangsan")
    );

    @Test
    public void testPredicateTest(){
        Predicate<Integer> predicate = x -> x >  0;
        System.out.println(predicate.test(100));


        Predicate<Integer> predicate2 = x -> x >100;
        predicate2 = predicate2.and(x -> x % 2 == 0 );
        System.out.println(predicate2.test(98));
        System.out.println(predicate2.test(102));
        System.out.println(predicate2.test(103));
    }

    @Test
    public void testPredicateNegate(){

        Predicate<Person> personPredicate = x -> x.age > 22;

        System.out.println(JSON.toJSONString(personList.stream().filter(personPredicate.negate()).collect(Collectors.toList())));

        Predicate<Person> predicate =  x -> x.name.equals("lisi");
        predicate = predicate.or(x -> x.age > 25);
        System.out.println(personList.stream().filter(predicate).count());

    }


    class Person{
        private int age;
        private String name;
        public Person(int age, String name) {
            this.age = age;
            this.name = name;
        }
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        public int getAge() {
            return age;
        }
        public void setAge(int age) {
            this.age = age;
        }
    }

}
