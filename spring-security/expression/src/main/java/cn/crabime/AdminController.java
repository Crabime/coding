package cn.crabime;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @RequestMapping("/")
    public String adminIndex() {
        return "admin";
    }

    @RequestMapping("/denyAll")
    @ResponseBody
    public String denyAll() {
        return "deny all request";
    }
}
