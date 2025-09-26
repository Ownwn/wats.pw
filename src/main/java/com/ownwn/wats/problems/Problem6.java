package com.ownwn.wats.problems;

import com.ownwn.wats.HomePage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Set;

@Controller
public class Problem6 {
    @SuppressWarnings("unused")
    public static final Set<String> banned = Set.of("toString()", "\"", "if", "getClass", "try", "do", "while", "?", "&&", "||", "^");
    public static final String challenge = """
                import java.util.function.*;
                public class Main {
                    public static void main(String[] arg) {
                        var s1 = Stack.<Integer>empty().push(3).push(2).push(1);
                        var s2 = Stack.<Integer>empty().push(6).push(5).push(4);
            
                        var r1 = zip((a, b) -> a + b, s1, s2);
                        assert r1.string().equals("5, 7, 9, EMPTY") : "Failed 1: " + r1.string();
            
                        var s3 = Stack.<Integer>empty().push(2).push(1);
                        var r2 = zip((a, b) -> a * b, s3, s2);
                        assert r2.string().equals("4, 10, EMPTY") : "Failed 2: " + r2.string();
            
                        var s4 = Stack.<String>empty().push("b").push("a");
                        var r3 = zip((i, s) -> s + i, s1, s4);
                        assert r3.string().equals("a1, b2, EMPTY") : "Failed 3: " + r3.string();
            
                        var s_empty = Stack.<Integer>empty();
                        var r4 = zip((a, b) -> a + b, s1, s_empty);
                        assert r4.string().equals("EMPTY") : "Failed 4: " + r4.string();
            
                        var r5 = zip((a, b) -> a + b, s_empty, s2);
                        assert r5.string().equals("EMPTY") : "Failed 5: " + r5.string();
            
                    }
                    
                    static <A, B, C> Stack<C> zip(BiFunction<A, B, C> f, Stack<A> as, Stack<B> bs) {
                        TARGET REPLACEMENT 473882928347567473734
                    }
                    
                    interface Stack<E> {
                        static <T> Stack<T> empty() { return new Stack<T>() {}; }
                        default <R> R match(Supplier<R> onEmpty, BiFunction<E, Stack<E>, R> onElem) {return onEmpty.get();}
            
                        default Stack<E> push(E e) {
                            var self = this;
                            return new Stack<E>() {
                                public <R> R match(Supplier<R> onEmpty, BiFunction<E, Stack<E>, R> onElem) {
                                    return onElem.apply(e, self);
                                }
                            };
                        }
                        default String string() {
                            return this.match(() -> "EMPTY", (h, t) -> h + ", " + t.string());
                        }
                    }
                }""";

    @GetMapping("/6")
    public String helloPage(Model m) {
        HomePage.addToModel(m, challenge, this.getClass());
        return "problem";
    }
}
