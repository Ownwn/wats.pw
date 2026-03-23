package com.ownwn.wats.problems;

import com.ownwn.wats.HomePage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Set;

@Controller
public class Problem7 {
    @SuppressWarnings("unused")
    public static final Set<String> banned = Set.of("String", "3", "4", "5", "6", "7", "getClass", "try", "do", "while", "?", "&&", "||", "^", "for", "Math", "math", "//", "/*", "base");
    public static final String challenge = """
                import java.util.function.*;
                public class Main {
                    public static void main(String[] arg) {
                        for (int i = 0; i < 100; i++) {
                            int k = (int) (Math.random() * 10);
                            assert base(k) == fact(k, (BiFunction<Integer, Object, Integer>) Main::fact);
                        }
            
                    }
                    
                    static int base(int i) {
                        if (i <= 1) return 1;
                        return i * base(i - 1);
                    }
                    
                    @SuppressWarnings("unchecked")
                    TARGET REPLACEMENT 473882928347567473734
                     
                }""";

    @GetMapping("/7")
    public String helloPage(Model m) {
        HomePage.addToModel(m, challenge, this.getClass());
        return "problem";
    }
}
