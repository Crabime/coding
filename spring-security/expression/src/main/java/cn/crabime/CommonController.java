package cn.crabime;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/common")
public class CommonController {

    @RequestMapping("/")
    public String common() {
        return "common";
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }
}
