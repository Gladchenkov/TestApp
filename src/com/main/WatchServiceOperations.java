package com.main;

import org.junit.Test;

import java.io.IOException;
import java.nio.file.*;

public class WatchServiceOperations {

    @Test
    public void watchServiceTest() throws IOException, InterruptedException {
        WatchService watchService = FileSystems.getDefault().newWatchService();
        Path path = Paths.get("D:\\work\\projects\\testapp\\src\\resources\\");
        path.register(watchService, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_MODIFY);
        WatchKey key;
        while ((key = watchService.take()) != null) {
            for (WatchEvent<?> event : key.pollEvents()) {
                System.out.println("Event kind:" + event.kind() + ". File affected: " + event.context() + ".");
                key.reset();
            }
        }
    }
}
