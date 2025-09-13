package com.ownwn.wats.problems;

import com.ownwn.wats.HomePage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Problem4 {
    public static final String challenge = """
                public class Main {
                    public static Class<Void> foo() {
                        TARGET REPLACEMENT 473882928347567473734
                    }
                    
                    public static void main(String[] args) {
                        var v = foo();
                        assert v != null;
                    }
                }""";

    @GetMapping("/4")
    public String helloPage(Model m) {
        HomePage.addToModel(m, challenge, this.getClass());
        return "problem";
    }
}
