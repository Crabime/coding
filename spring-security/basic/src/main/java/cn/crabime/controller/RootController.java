package cn.crabime.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class RootController {

    @RequestMapping("/a")
    public String adminPage() {
        return "/adminPage";
    }
}
