package com.ownwn.wats.problems;

import com.ownwn.wats.HomePage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Problem2 {
    public static final String challenge = """
            public class Main {
                public static void main(String[] args) {
                    assert stuff().equals(1234);
                }
            
                TARGET REPLACEMENT 473882928347567473734
            }""";

    @GetMapping("/2")
    public String helloPage(Model m) {
        HomePage.addToModel(m, challenge, this.getClass());
        return "problem";
    }
}
