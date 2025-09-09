package com.ownwn.wats;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomePage {
    @GetMapping("/")
    public String helloPage() {
        return "homePage.html";
    }
}
