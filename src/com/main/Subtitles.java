package com.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class Subtitles {
    public static void main(String[] args) throws IOException {

        String dir = "I:\\Serials\\Star Wars The Clone Wars\\Season_02\\Subtitles\\English\\";

        List<String> collect1 = Files.list(Paths.get(dir))
                .map(Path::toFile)
                .map(File::getAbsolutePath)
                .collect(Collectors.toList());

        List<Path> files = Files.walk(Paths.get(dir))
                .filter(Files::isRegularFile)
                .collect(Collectors.toList());

        for (Path file : files) {
            try (BufferedReader br = Files.newBufferedReader(file)) {
                List<String> content = br.lines()
                        .filter(line -> !line.contains("-->"))
                        .filter(line -> !line.matches(".*[0-9].*"))
                        .map(line -> line.replaceAll("<[^>]*>", ""))
                        .collect(Collectors.toList());

                Files.write(Paths.get(dir + file.getFileName() + "._cleaned.txt"), content);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
