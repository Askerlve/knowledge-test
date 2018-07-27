package com.askerlve.knowledge.collections;

import org.junit.Test;

import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Askerlve
 * @Description: TODO
 * @date 2018/7/12下午8:01
 */
public class CollectionsTest {

    private List<Person> personList = Arrays.asList(
            new Person("shenzhen", "张", 1),
            new Person("beijing", "王", 2),
            new Person("shanghai", "李", 3),
            new Person("beijing", "赵", 4));

    @Test
    public void testCollections() {
        // 将Stream转换为HashSet集合
        HashSet<Integer> hashSet = Stream.of(1, 2, 4, 5).collect(Collectors.toCollection(HashSet::new));

        // 没有分隔符，默认直接拼接，打印出  1234
        String join = Stream.of("1", "2", "3", "4").collect(Collectors.joining());
        System.out.println(join);
        // 使用逗号分隔符，打印出   1,2,3,4
        join = Stream.of("1", "2", "3", "4").collect(Collectors.joining(","));
        System.out.println(join);
        // 使用逗号分隔符，并且给最终拼接的字符串添加前后缀， 打印  [1,2,3,4]
        join = Stream.of("1", "2", "3", "4").collect(Collectors.joining(",", "[", "]"));
        System.out.println(join);
    }

    @Test
    public void testGroupBy() {
        Map<String, List<Person>> map = personList.stream().collect(Collectors.groupingBy(Person::getCity));
        System.out.println(map);

        Map<String, Integer> integerMap = personList.stream().collect(Collectors.groupingBy(Person::getCity,
                Collectors.summingInt(Person::getSum)));
        System.out.println(integerMap);

        Function<Person, String> stringStringFunction = Person::getCity;

        Map<String, Integer> stringIntegerMap = personList.stream().collect(Collectors.groupingBy(stringStringFunction,
                TreeMap::new, Collectors.summingInt(Person::getSum)));
        System.out.println(stringIntegerMap);

    }

    public List<Person> initPerson() {
        return Arrays.asList(new Person("shenzhen", "张", 1),
                new Person("beijing", "王", 2),
                new Person("shanghai", "李", 3));
    }

    public static List<Person> initPerson2() {
        return Arrays.asList(new Person("shenzhen", "张", 1),
                new Person("beijing", "王", 2),
                new Person("shanghai", "李", 3),
                new Person("beijing", "张", 4));
    }


    @Test
    public void testToMap() {
        //不允许key重复
        Map<String, Integer> map = initPerson().stream().collect(Collectors.toMap(Person::getCity, Person::getSum));
        System.out.println(map);

        /**
         * 使用旧的值，本例子中结果打印：{shanghai=3, shenzhen=1, beijing=2}
         * (f, s) -> f
         * 使用新的值替换旧的值，打印：{shanghai=3, shenzhen=1, beijing=4}
         * (f, s) -> s
         * 对新值和旧值进行处理，比如返回新值与旧值的和：{shanghai=3, shenzhen=1, beijing=6}
         * (f, s) -> f + s
         */
        Map<String, Integer> stringIntegerMap = initPerson2().stream().collect(
                Collectors.toMap(Person::getCity, Person::getSum, (f, s) -> f + s));
        System.out.println(stringIntegerMap);


        Map<String, Integer> treeMap = initPerson2().stream().collect(
                Collectors.toMap(Person::getCity, Person::getSum, (f, s) -> s + f, TreeMap::new));
        System.out.println(treeMap);

        //该方法也是用于分组，不过是根据某一条件进行分组，最终分成满足条件的true和不满足条件的false两个分组
        Map<Boolean, List<Person>> booleanListMap = initPerson2().stream().collect(
                Collectors.partitioningBy(input -> input.getSum() >= 2));
        System.out.println(booleanListMap);

        Map<Boolean, Map<Boolean, List<Person>>> booleanMapMap = initPerson2().stream().collect(
                Collectors.partitioningBy(input -> input.getSum() >= 2,
                        Collectors.partitioningBy(input -> "beijing".equals(input.getCity()))));
        System.out.println(booleanMapMap);

        Map<Boolean, Long> booleanMapMap2 = initPerson2().stream().collect(
                //非线程安全
                Collectors.partitioningBy(input -> input.getSum() >= 2, Collectors.counting()));

        System.out.println(booleanMapMap2);

    }


    /**
     * 该方法和Stream中的reduce方法功能类似，用来执行一些二目运算的操作，比如数值类型的求和，
     * 求最大值，求最小值，字符串连接，集合类型的交集并集等操作
     */
    @Test
    public void testReducing() {
        // 根据条件过滤分组后，保留最大的
        Comparator<Person> sumComparator = Comparator.comparing(Person::getSum);
        Map<Boolean, Optional<Person>> maxBoolMap = initPerson2().stream().collect(
                Collectors.partitioningBy(input -> input.getSum() >= 2,
                        Collectors.reducing(BinaryOperator.maxBy(sumComparator))));
        System.out.println(maxBoolMap);

        // 分组，保留组内key相同的最后一个
        Map<String, Optional<Person>> maxMap = initPerson2().stream().collect(Collectors.groupingBy(Person::getCity,
                Collectors.reducing((f, s) -> s)));
        System.out.println(maxMap);
    }


    /**
     * maxBy/minBy方法
     */
    @Test
    public void testMaxByAndMinBy() {
        // 根据条件过滤分组后，保留最大的
        Comparator<Person> sumComparator = Comparator.comparing(Person::getSum);
        Map<Boolean, Optional<Person>> maxBoolMap = personList.stream().collect(
                Collectors.partitioningBy(input -> input.getSum() >= 2,
                        Collectors.minBy(sumComparator)));
        System.out.println(maxBoolMap);
    }

    /**
     * 该方法接收两个参数，表示在第一个参数执行基础上，再执行第二个参数对应的函数表达式
     */
    @Test
    public void testCollectingAndThen(){

        List<Integer> list = Arrays.asList(1, 2, 3, 4);
        Double retVal = list.stream().collect(Collectors.averagingInt(v -> v));
        System.out.println(retVal);
        Double result = list.stream().collect(Collectors.collectingAndThen(Collectors.averagingInt(v -> v),
                s -> s * s));
        // output: 6.25
        System.out.println(result);

    }

    /**
     * mapping
     */
    @Test
    public void testMapping(){
        // 根据条件过滤分组后，获取组内元素的surname，并用逗号分割
        Map<String, String> nameByCity
                = personList.stream().collect(Collectors.groupingBy(Person::getCity,
                Collectors.mapping(Person::getSurname, Collectors.joining(","))));

        System.out.println(nameByCity);

        Map<String, List<String>> sumByCity
                = personList.stream().collect(Collectors.groupingBy(Person::getCity,
                Collectors.mapping(Person::getSurname,Collectors.toList())));

        System.out.println(sumByCity);

    }


}

class Person {
    private String city;
    private String surname;
    private Integer sum;

    public Person(String city, String surname, Integer sum) {
        this.city = city;
        this.surname = surname;
        this.sum = sum;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Integer getSum() {
        return sum;
    }

    public void setSum(Integer sum) {
        this.sum = sum;
    }

    @Override
    public String toString() {
        return "Person{" +
                "city='" + city + '\'' +
                ", surname='" + surname + '\'' +
                ", sum=" + sum +
                '}';
    }
}
