package com.ownwn.wats.problems;

import com.ownwn.wats.HomePage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Problem3 {
    public static final String challenge = """
                import java.util.function.BiFunction;
                import java.util.function.Supplier;
                interface Stack<T> {
                    static <T> Stack<T> empty() { return new Stack<>(){}; }
                
                    default <R> R match(Supplier<R> onEmpty, BiFunction<T, Stack<T>, R> onElem) { return onEmpty.get(); }
                    default Stack<T> push(T t) {
                        var self = this;
                        return new Stack<>() {
                            TARGET REPLACEMENT 473882928347567473734
                        };
                    }
                }
                public class Main {
                    public static void main(String[] args) {
                        Stack<String> stack = Stack.<String>empty();
                        assert stack.push("hello").match(() -> "FAIL", (elem, tail) -> elem).equals("hello");
                    }
                }""";

    @GetMapping("/3")
    public String helloPage(Model m) {
        HomePage.addToModel(m, challenge, this.getClass());
        return "problem";
    }
}
