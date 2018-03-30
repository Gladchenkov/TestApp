package com.main;


import org.junit.Test;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class StreamCreate {

    @Test
    public void create_empty_streams() {
        Stream<String> emptyStream1 = Stream.empty();
        Stream<Integer> emptyStream2 = Stream.empty();
        Stream<User> emptyStream3 = Stream.empty();
    }

    @Test
    public void create_stream_from_arrays() {
        String[] arr = {"program", "creek", "program", "creek", "java", "web", "program"};
        int[] integers = {1, 4, 6, 2, 6, 3, 2};
        //way 1
        Stream<String> stream1 = Stream.of(arr);
        //way 2
        Stream<String> stream2 = Stream.of("program", "creek", "program", "creek", "java", "web", "program");
        //way 3
        Stream<String> stream3 = Arrays.stream(arr);
        //way 4
        IntStream stream4 = Arrays.stream(integers);
    }

    @Test
    public void create_stream_from_collections() {
        List<String> list = new ArrayList<>();
        list.add("java");
        list.add("php");
        list.add("python");

        //way 1
        Stream<String> stream1 = list.stream();

        //way 2
        Collection<String> collection = Arrays.asList("a", "b", "c");
        Stream<String> stream2 = collection.stream();
    }

    @Test
    public void create_stream_using_builder() {
        //way 1
        Stream<String> streamBuilder = Stream.<String>builder()
                .add("a")
                .add("b")
                .add("c")
                .build();
        //way 2
        Stream<Object> stream  =  Stream.builder()
                .add("WWW")
                .add(".")
                .add("NOUR-IT")
                .add(".")
                .add("COM")
                .build();
    }

    @Test
    public void create_stream_using_generate() {
        //way 1
        Stream.generate(() -> Double.valueOf(Math.random() * 50).intValue()).limit(5).forEach(System.out::println);
        //way 2
        Stream.generate(() -> "test").limit(5).forEach(System.out::println);
        //way 3
        Stream<Double> randomNumbers = Stream.generate(Math::random);
    }

    @Test
    public void create_stream_using_iterate() {
        //way 1
        Stream.iterate(BigInteger.ZERO, n -> n.add(BigInteger.ONE)).limit(10).forEach(System.out::println);
        //way 2
        Stream<Integer> streamIterated = Stream.iterate(40, n -> n + 2).limit(20);
        streamIterated.forEach(System.out::print);
        Stream<Integer> evenNumbers = Stream.iterate(0, n -> n + 2).limit(20);
        evenNumbers.forEach(System.out::print);
    }

    @Test
    public void create_stream_from_files() throws IOException {
        //way 1 file lines
        Stream<String> lines = Files.lines(Paths.get("/tmp/data"), Charset.forName("UTF-8"));
        //way 2 list of files
        Stream<Path> list = Files.list(Paths.get("/tmp/data"));
    }

    @Test
    public void create_infinite_stream(){
        //way
        Stream<Integer> infiniteStream = Stream.iterate(0, i -> i + 2);

        List<Integer> collect = infiniteStream
                .limit(10)
                .collect(Collectors.toList());
        collect.forEach(System.out::println);


    }
}
