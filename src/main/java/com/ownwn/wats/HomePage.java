package com.ownwn.wats;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomePage {
    @GetMapping("/")
    public String helloPage() {
        return "home";
    }

    public static void addToModel(Model m, String challenge, Class<?> clazz) {
        m.addAttribute("challenge", challenge);
        String clazzName = clazz.getName();
        m.addAttribute("id", clazzName.substring(clazzName.length()-1));
    }
}
