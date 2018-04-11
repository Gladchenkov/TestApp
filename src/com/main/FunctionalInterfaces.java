package com.main;


import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntSupplier;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

/**
 * Created by Siarhei_Hladchankou on 4.12.2017.
 */

public class FunctionalInterfaces

{
    @Test
    public void funcInterface() {
        SimpleFuncInterface<String> funcInterface = (a) -> System.out.println(a + "Do work in SimpleFun impl...");
        funcInterface.doWork("String");
    }

    @Test
    public void funcInterface_supplier() {
        Supplier<List<String>> sup = () -> new ArrayList<>();
        System.out.println(sup.get());
    }

    @Test
    public void funcInterface_consumer() {
        Consumer<String> sup = (a) -> System.out.println(a);
        sup.accept("Fuck");
    }

    @Test
    public void funcInterface_consumer2() {
        List<String> names = Arrays.asList("John", "Freddy", "Samuel");
        names.forEach(name -> System.out.println("Hello, " + name));

        Map<String, Integer> ages = new HashMap<>();
        ages.put("John", 25);
        ages.put("Freddy", 24);
        ages.put("Samuel", 30);

        ages.forEach((name, age) -> System.out.println(name + " is " + age + " years old"));
    }

    @Test
    public void funcInterface_consumer3() {
        List<Integer> ages1 = Arrays.asList(1, 5, 15, 56, 30, 133);
        List<Integer> ages2 = Arrays.asList(12, 34, 66, 77, 88);
        List<Integer> ages3 = Arrays.asList(76, 54, 87, 32, 50);
        Map<String, List<Integer>> nameAges = new HashMap<>();
        nameAges.put("John", ages1);
        nameAges.put("Freddy", ages2);
        nameAges.put("Samuel", ages3);
        Map<Integer, String> agesNames = new HashMap<>();

        nameAges.forEach((a, b) -> b.forEach(c -> agesNames.put(c, a)));
        agesNames.forEach((age, name) -> System.out.println(age + " is " + name));
    }

    @Test
    public void funcInterface_BiConsumer() {
        Map<String, Integer> map = new HashMap<>();
        BiConsumer<String, Integer> consumer = (a, b) -> map.put(a, b);
        consumer.accept("Fuck", 1);
        System.out.println(map);
    }

    @Test
    public void funcInterface_predicate() {
        Predicate<String> predicate = (a) -> "Fuck".equals(a);

        Predicate<String> predicate2 = (a) -> a.isEmpty();

        System.out.println("TEST " + predicate.test("FUCK"));

        Predicate<String> negate = predicate.negate();
        System.out.println("negate " + negate.test("FUCK"));

        System.out.println("isEqual " + Predicate.isEqual(predicate));
    }

    @Test
    public void funcInterface_predicate2() {
        List<String> names = Arrays.asList("Angela", "Aaron", "Bob", "Claire", "David");

        names = names.stream().filter(name -> name.startsWith("A")).collect(Collectors.toList());
        names.stream().forEach(System.out::println);
        //		names.stream()
        //				.filter(name -> name.startsWith("A"))
        //				.collect(Collectors.toList())
        //				.forEach(System.out::println);
    }

    @Test
    public void funcInterface_function() {
        Function<String, Integer> function = (a) ->
        {
            int i = a.length() + 100;
            i = i * 200;
            return i;
        };

        System.out.println(function.apply("Fuck"));
    }

    @Test
    public void funcInterface_function2() {
        Function<String, String> function = String::toUpperCase;

        System.out.println(function.apply("fuck"));
    }

    @Test
    public void funcInterface_function3() {
        Map<String, Integer> nameMap = new HashMap<>();
        nameMap.put("John", 1);
        Integer value = nameMap.computeIfAbsent("John", s -> s.length());
        System.out.println(value);
    }

    @Test
    public void funcInterface_function4() {
        Function<Integer, String> intToString = Object::toString;
        Function<String, String> quote = s -> "'" + s + "'";

        Function<Integer, String> quoteIntToString = quote.compose(intToString);

        System.out.println(quoteIntToString.apply(10));
    }

    @Test
    public void funcInterface_biFunction() {
        BiFunction<String, String, Integer> function = (a, b) -> a.length() + b.length();
        System.out.println(function.apply("fuck", "FUCK"));
    }

    @Test
    public void funcInterface_biFunction2() {
        Map<String, Integer> salaries = new HashMap<>();
        salaries.put("John", 40000);
        salaries.put("Freddy", 30000);
        salaries.put("Samuel", 50000);

        salaries.replaceAll((name, oldValue) ->
                name.equals("Freddy") ? oldValue : oldValue + 10000);

        System.out.println(salaries);
    }

    @Test
    public void funcInterface_unaryOperator() {
        UnaryOperator<String> function = (a) -> a.toLowerCase();
        System.out.println(function.apply("FUCK"));
    }

    @Test
    public void funcInterface_unaryOperator2() {
        List<String> names = Arrays.asList("bob", "josh", "megan");

        names.replaceAll(String::toUpperCase);
        names.forEach(System.out::println);
    }

    @Test
    public void funcInterface_binaryOperator() {
        BinaryOperator<String> function = (a, b) -> a + " " + b;
        System.out.println(function.apply("fuck", "FUCK"));
    }

    @Test
    public void funcInterface_binaryOperator2() {
        List<Integer> values = Arrays.asList(3, 5, 8, 9, 12);
        //way 1
        int sum = values.stream()
                .reduce(0, (i1, i2) -> i1 + i2);
        System.out.println(sum);

        //way 2
        int sum2 = values.stream()
                .reduce(0, (a, b) -> Integer.sum(a, b));
        System.out.println(sum2);

        //way 3
        int sum3 = values.stream()
                .reduce(0, Integer::sum);
        System.out.println(sum3);
    }

    @Test
    public void funcInterface_binaryOperator3() {
        List<String> values = Arrays.asList("Angela", "Aaron", "Bob", "Claire", "David");
        //way 1
        String sum = values.stream().reduce("", String::concat);
        System.out.println(sum);
    }

    @Test
    public void funcInterface_intSupplier() {
        IntSupplier function = () -> 1;
        System.out.println(function.getAsInt());
    }
}
