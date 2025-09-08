package com.ownwn.wats.problems;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Problem1 {
    @GetMapping("/1")
    public String helloPage() {
        return "1.html";
    }
}
