package cn.crabime.mvc.basic;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class IndexController {

    @RequestMapping("/")
    public String index() {
        return "index";
    }

    @RequestMapping("/abc")
    @ResponseBody
    public String helloWorld() {
        return "hello world";
    }
}
