package com.main;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class StreamOperations {

    final static Logger log = Logger.getLogger(StreamOperations.class);
    private List<User> users;
    private List<String> strings;

    @Before
    public void setup() {
        User user = new User();
        user.setName("Fucker1");
        user.setAge(23);
        Address address1 = new Address("Minsk", "Shpilevskogo", 56);
        Address address2 = new Address("London", "Street", 100);
        Address address3 = new Address("Barcelona", "Avenue", 1);
        user.setAddresses(new ArrayList<>(Arrays.asList(address1, address2, address3)));

        User user2 = new User();
        user2.setName("Fuck");
        user2.setAge(18);
        Address address4 = new Address("Grodno", "Shpilevskogo", 56);
        Address address5 = new Address("Krakow", "Street", 100);
        Address address6 = new Address("Moskow", "Avenue", 1);
        user2.setAddresses(new ArrayList<>(Arrays.asList(address4, address5, address6)));

        User user3 = new User();
        user3.setName("Motherfucker");
        user3.setAge(20);
        Address address7 = new Address("Vitebsk", "Shpilevskogo", 56);
        Address address8 = new Address("Vilnus", "Street", 100);
        Address address9 = new Address("Praha", "Avenue", 1);
        user3.setAddresses(new ArrayList<>(Arrays.asList(address7, address8, address9)));

        users = new ArrayList<>((Arrays.asList(user, user2, user3)));

        strings = new ArrayList<>(Arrays.asList("abc", "", "bcd", "", "defg", "jk", "program", "creek", "program", "creek", "java", "web", "program"));
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
        //way 2
        int reducedTwoParams = IntStream.range(1, 4).reduce(10, (a, b) -> a + b);
        log.info(reducedTwoParams);  // = 16 (10 + 1 + 2 + 3)
        //way 3  get min value
        Integer min = users.stream()
                .map(User::getAge)
                .reduce(Integer::min)  //don't do that (insidious boxing cost) use mapToInt
                .get();
        log.info(min);

        //way 4  get max value
        Integer max = users.stream()
                .map(User::getAge)
                .reduce(Integer::max)  //don't do that (insidious boxing cost) use mapToInt
                .get();
        log.info(max);

    }

    @Test
    public void removeIf() {
        users.forEach(System.out::println);
        users.removeIf(x -> x.getAge() == 18);
        users.forEach(System.out::println);
    }

    @Test
    public void flatMap() {
        //without flatMap
        List<List<Address>> collect = users.stream()
                .map(User::getAddresses)
                .collect(Collectors.toList());

        //with flatMap
        List<Address> addresses = users.stream()
                .flatMap(user -> user.getAddresses().stream())
                .collect(Collectors.toList());
    }

    @Test
    public void sort() {
        //nature order
        List<Address> addresses = users.stream()
                .flatMap(user -> user.getAddresses().stream())
                .sorted(Comparator.comparing(Address::getCity))
                .collect(Collectors.toList());
        addresses.forEach(log::info);

        log.info("========================================");
        //reversed order
        List<Address> reversed = users.stream()
                .flatMap(user -> user.getAddresses().stream())
                .sorted(Comparator.comparing(Address::getCity).reversed())
                .collect(Collectors.toList());
        reversed.forEach(log::info);
    }

    @Test
    public void collect() {
        Map<String, Address> collect = users.stream()
                .flatMap(user -> user.getAddresses().stream())
                .collect(Collectors.toMap(Address::getCity, Function.identity())); //Address::getCity is the key and Function.identity() is the Address itself
        log.info(collect);
    }

    @Test
    public void boxed() {
        IntStream create = IntStream.of(1, 2, 3);
        IntStream intStream = users.stream().mapToInt(User::getAge);

        Stream<Integer> boxed1 = create.boxed();
        Stream<Integer> boxed2 = intStream.boxed();
    }

    @Test
    public void rangeClosed() {

    }
}
