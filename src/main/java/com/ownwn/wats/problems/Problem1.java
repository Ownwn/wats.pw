package com.ownwn.wats.problems;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Problem1 {
    public final String challenge = """
            public class Main {
                public static void main(String[] args) {
                    assert stuff().equals("unlocked door");
                }
            
                public static String stuff() {
                    TARGET REPLACEMENT 473882928347567473734
                }
            }""";

    @GetMapping("/1")
    public String helloPage(Model m) {
        m.addAttribute("challenge", challenge);
        return "1";
    }
}
