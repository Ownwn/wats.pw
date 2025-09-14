package com.ownwn.wats.problems;

import com.ownwn.wats.HomePage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Set;

@Controller
public class Problem5 {
    @SuppressWarnings("unused")
    public static final Set<String> banned = Set.of("\"", "(", ")", "+");
    public static final String challenge = """
                public class Main {
                    public static String log = "";
                    
                    public static void main(String[] args) {
                        new Main().main();
                        
                        assert log.trim().equals("TARGET REPLACEMENT 473882928347567473734".trim());
                    }
                    
                    public void main() {
                        new A().new B().new C().new D().run();
                        Foo.FIRST.bar();
            
                        log+= "_";
                        runnable1();
                        log+= "_";
                        runnable2();
                    }
            
                    void runnable1() {
                        Runnable a = () -> {};
                        Runnable b = () -> {};
                        log+= a == b;
                        log+= a.equals(b);
                    }
            
                    void runnable2() {
                        Runnable a = System.out::println;
                        Runnable b = () -> System.out.println();
                        log+= a == b;
                        log+= a.equals(b);
                    }
            
                    class A {
                        A() { log+="A"; }
            
                        class B {
                            B() { log+="B"; }
            
                            class C {
                                C() { log+="C"; }
            
                                class D {
                                    D() { log+="D"; }
            
                                    void run() { A.this.new B().new C().new D(); }
                                }
                            }
                        }
                    }
            
                    enum Foo {
                        FIRST, SECOND;
                        int i = 0;
                        Foo() {
                            log+="E";
                        }
                        void bar() {}
                    }
                }""";

    @GetMapping("/5")
    public String helloPage(Model m) {
        HomePage.addToModel(m, challenge, this.getClass());
        return "problem";
    }
}
