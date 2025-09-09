package com.ownwn.wats.problems;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class Checker {
    private static final AtomicLong lastRequest = new AtomicLong(0);


    @PostMapping("check")
    public String check(@RequestBody String input) {
        if (System.currentTimeMillis() - lastRequest.get() < 2000L) {
            return "STOP SPAMMING!!";
        }
        lastRequest.set(System.currentTimeMillis());
        List<File> files = Arrays.stream(Path.of("dock").toAbsolutePath().toFile().listFiles()).toList();


        return isGood(input) ? "Good!" : "Fail";
    }

    private boolean isGood(String input) {
        return input.contains("key");
    }
}
