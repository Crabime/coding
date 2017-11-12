package com.gooalgene.controller;

import com.gooalgene.beans.User;
import com.gooalgene.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by crabime on 11/5/17.
 * Mainly used to store data in model and transport it into front view
 */
@Controller
@RequestMapping(value = "/model")
public class ModelController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/simpleModel")
    public String simpleModel(Model model) throws Exception {
        model.addAttribute("name", "张三");
        return "simple-model";
    }

    @RequestMapping(value = "/cookieModel", method = RequestMethod.GET)
    public String cookieModel(Model model, Integer id){
        User user = userService.findUserById(id);
        String username = null;
        Cookie cookie = null;
        if (user != null){
            username = user.getName();
            //目前cookie测试还不成功
            cookie = new Cookie("name", username);
            cookie.setComment("id为:" + id + "的名字");
            cookie.setMaxAge(3000);
            model.addAttribute("name", username);
        }else {
            model.addAttribute("name", null);
        }
        return "simple-model";
    }

    @RequestMapping(value = "/modelViewCombing", method = RequestMethod.GET)
    public ModelAndView forward(){
        ModelAndView modelAndView = new ModelAndView("/sophistic-model");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", "crabime");
        modelAndView.addAllObjects(map);
        return modelAndView;
    }
}
