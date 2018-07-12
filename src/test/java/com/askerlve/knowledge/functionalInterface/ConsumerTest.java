package com.askerlve.knowledge.functionalInterface;

import com.alibaba.fastjson.JSON;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

/**
 * @author Askerlve
 * @Description: TODO
 * @date 2018/7/12下午4:03
 */
public class ConsumerTest {

    private List<Person> sourceList = Arrays.asList(
            new Person(21,"zhangsan"),
            new Person(22,"lisi"),
            new Person(23,"wangwu"),
            new Person(24,"wangwu"),
            new Person(23,"lisi"),
            new Person(26,"lisi"),
            new Person(26,"zhangsan"));

    @Test
    public void testConsumerAccept(){
        List<Person> lisiList = new ArrayList<>();
        Consumer<Person> consumer  = x -> {
            if (x.getName().equals("lisi")){
                lisiList.add(x);
            }
        };
        sourceList.forEach(consumer);

        System.out.println(JSON.toJSONString(lisiList));
    }

    @Test
    public void testConsumerAndThen(){

        List<Person> lisiList = new ArrayList<>();

        Consumer <Person> consumer  =  x -> {
            if (x.getName().equals("lisi")){
                lisiList.add(x);
            }
        };

        consumer = consumer.andThen(
                x -> lisiList.removeIf(y -> y.getAge() < 23)
        ).andThen(z -> lisiList.removeIf(o -> o.getAge() == 26));

        sourceList.forEach(consumer);

        System.out.println(JSON.toJSONString(lisiList));
    }

}

class Person{

    private int age;

    private String name;

    public Person(int age, String name) {
        this.age = age;
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
