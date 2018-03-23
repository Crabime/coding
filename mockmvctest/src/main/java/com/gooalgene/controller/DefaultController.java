package com.gooalgene.controller;

import com.gooalgene.annotation.HelloMessageGenerator;
import com.gooalgene.beans.User;
import com.gooalgene.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by crabime on 11/2/17.
 * 为什么这里的controller找不到?
 */
@Controller
public class DefaultController {

    @Autowired
    private UserService userService;

    @Resource(name = "requestMessage")
    HelloMessageGenerator requestMessage;

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index(){
        System.out.println("到这里来了吗????");
        return "index";
    }

    @RequestMapping("/scopes")
    public String getScopes(Model model) {
        requestMessage.setMessage("Good morning!");
        model.addAttribute("requestMessage", requestMessage.getMessage());
        return "scopesExample";
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    @ResponseBody
    public String getScopesSecondTime(){
        return requestMessage.getMessage();
    }

    @RequestMapping(value = "/findAll", method = RequestMethod.GET)
    @ResponseBody
    public List<User> findAllUsers(){
        return userService.findAllUsers();
    }

    @RequestMapping(value = "/findByName", method = RequestMethod.GET)
    @ResponseBody
    public User findUserByName(@RequestParam(name = "name", required = true)String name){
        return userService.findUserByName(name);
    }
}
