package com.main;



import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by Siarhei_Hladchankou on 21.12.2017.
 */
public class OptionalTests {
    @Test
    public void emptyOptional() {
        Optional<String> empty = Optional.empty();
        boolean present = empty.isPresent();

        Optional<String> empty4 = Optional.empty();
        boolean present4 = empty.isPresent();

        Optional<String> notEmpty = Optional.of("fuck");
        boolean present2 = notEmpty.isPresent();
        String str2 = notEmpty.get();

        Optional<String> nullable = Optional.ofNullable(null);
        boolean present3 = nullable.isPresent();

        //		Optional<String> exception = Optional.of(null);

    }

    @Test
    public void getIfValueIsNotNullInOptional() {
        Optional<String> notEmpty = Optional.of("fuck");
        Optional<String> nullable = Optional.ofNullable(null);

        //return "WTF" if notEmpty is null
        String str = nullable.orElse("WTF");

        //return notEmpty if notEmpty is not null
        String str2 = notEmpty.orElse("WTF");

        //do sout if value is present
        notEmpty.ifPresent(System.out::println);

        //return notEmpty if notEmpty is not null
        String str3 = notEmpty.orElseGet(() -> "WTF");

        //return "WTF" if notEmpty is null
        String str4 = nullable.orElseGet(() -> "WTF");

        //throw exc if notEmpty is null
        String str5 = notEmpty.orElseThrow(IllegalStateException::new);
    }

    @Test
    public void filterOptional() {
        User user = new User();
        user.setName("Fucker");
        user.setAge(23);
        Optional<User> notEmpty = Optional.of(user);
        notEmpty.filter(usr -> "Fucker".equals(usr.getName()))
                .ifPresent((c) -> System.out.println("WTF"));
    }

    @Test
    public void mapOptional() {
        User user = new User();
        user.setName("Fucker1");
        user.setAge(23);

        User user2 = new User();
        user2.setName("Fuck");
        user2.setAge(18);

        User user3 = new User();
        user3.setName("Motherfucker");
        user3.setAge(20);

        List<User> users = Arrays.asList(user, user2, user3);
        List<List<User>> notEmpty = Arrays.asList(
                Arrays.asList(user, user2, user3),
                Arrays.asList(user, user2, user3),
                Arrays.asList(user, user2, user3));
        Optional<User> notEmpty2 = Optional.of(user);
        Optional<String> notEmpty3 = Optional.of("fuck");

        notEmpty2.map(User::getName)
                .filter("Fucker"::equals)
                .ifPresent((c) -> System.out.println("WTF"));

        String str = notEmpty3.map(String::toUpperCase).get();

        System.out.println(notEmpty
                .stream()
                .flatMap(c -> c.stream().map(User::getName))
                .collect(Collectors.toList()));

        for (int i = 0; i < 10; i++) {
            System.out.println((Integer) i);
        }
    }

    @Test
    public void referenceOptional() {
        String str = "FUCK";
        Optional<String> str1 = Optional.of(str);
        System.out.println(str1);
        str = "MOTHERFUCKER";
        System.out.println(str1);

        User user = new User();
        user.setName("Fucker");
        user.setAge(23);
        Optional<User> optional = Optional.of(user);
        System.out.println(optional);
        user.setAge(100);
        System.out.println(optional);
    }
}
