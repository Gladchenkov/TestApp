package com.main;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.stream.IntStream;

public class StreamOperations {

    private List<User> users;
    private List<String> strings;
    private final static Logger log = Logger.getLogger(StreamOperations.class);

    @Before
    public void setup() {
        User user = new User();
        user.setName("Fucker1");
        user.setAge(23);

        User user2 = new User();
        user2.setName("Fuck");
        user2.setAge(18);

        User user3 = new User();
        user3.setName("Motherfucker");
        user3.setAge(20);

        users = Arrays.asList(user, user2, user3);

        strings = Arrays.asList("abc", "", "bcd", "", "defg", "jk", "program", "creek", "program", "creek", "java", "web", "program");
    }

    @Test
    public void filters() {
        long count = strings.stream()
                .filter(x -> x != null)                              // not null
                .filter(String::isEmpty)                             // is empty
                .filter(x -> x.length() > 3)                         // length more than 3
                .filter(x -> x.startsWith("a"))                      // starts with "a"
                .filter(x -> x.equals("abc"))                        // equals abc
                .filter(x -> x.equals("123") && 20 == x.length())    // 2 conditions
                .count();

        log.info(count);
    }

    @Test
    public void findAny() {
        //way 1
        Optional<String> any = strings.stream()
                .filter(x -> x.length() > 2)
                .findAny();  //returns optional
        //way 2
        String str = strings.stream()
                .filter(x -> x.length() > 2)
                .findAny()
                .get();
        //way 3
        String str2 = strings.stream()
                .filter(x -> x.length() > 2)
                .findAny()
                .orElse(null);

        log.info(str2);
    }

    @Test
    public void findFirst() {
        //way 1
        Optional<String> first = strings.stream()
                .filter(x -> x.length() > 2)
                .findFirst();  //returns optional
        //way 2
        String str = strings.stream()
                .filter(x -> x.length() > 2)
                .findFirst()
                .orElse(null);

        log.info(str);
    }

    @Test
    public void skip() {
        strings.stream()
                .skip(5)  //skip first 5
                .forEach(System.out::println);
    }

    @Test
    public void limit() {
        strings.stream()
                .limit(5)  //take first 5
                .forEach(System.out::println);
    }

    @Test
    public void anyMatch() {
        boolean value1 = strings.stream().anyMatch(x -> x.equals("abc"));
        boolean value2 = strings.stream().anyMatch(x -> x.equals("1"));
        log.info(value1);
        log.info(value2);
    }

    @Test
    public void allMatch() {
        boolean value1 = strings.stream().allMatch(x -> x != null);
        boolean value2 = strings.stream().allMatch(x -> x.equals("1"));
        log.info(value1);
        log.info(value2);
    }

    @Test
    public void noneMatch() {
        boolean value1 = strings.stream().noneMatch(x -> x == null);
        boolean value2 = strings.stream().noneMatch(x -> x.equals("1"));
        log.info(value1);
        log.info(value2);
    }

    @Test
    public void distinct() {
        // remove duplicates
        strings.stream().distinct().forEach(System.out::println);
    }

    @Test
    public void max_min_sum() {
        int max = users.stream()
                .mapToInt(User::getAge)
                .max()
                .orElse(0);

        int min = users.stream()
                .mapToInt(User::getAge)
                .min()
                .orElse(0);

        int sum = users.stream()
                .mapToInt(User::getAge)
                .sum();

        log.info(max);
        log.info(min);
        log.info(sum);
    }

    @Test
    public void reduce() {
        //way 1
        OptionalInt reduced = IntStream.range(1, 4).reduce((a, b) -> a + b);
        log.info(reduced.getAsInt());  // = 6 (1 + 2 + 3)
        // way 2
        int reducedTwoParams = IntStream.range(1, 4).reduce(10, (a, b) -> a + b);
        log.info(reducedTwoParams);  // = 16 (10 + 1 + 2 + 3)
    }

    @Test
    public void map() {
        users.stream()
                .map(user -> {
                    user.setAge(0);
                    return user;
                })
                .forEach(System.out::println);
    }
}
