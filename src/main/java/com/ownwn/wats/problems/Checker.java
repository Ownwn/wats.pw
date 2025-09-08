package com.ownwn.wats.problems;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Checker {
    @PostMapping("check")
    public String check(@RequestBody String input) {
        return input.contains("key") ? "good!" : "fail :(";
    }
}
