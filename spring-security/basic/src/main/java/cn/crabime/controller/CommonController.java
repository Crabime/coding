package cn.crabime.controller;

import cn.crabime.beans.User;
import cn.crabime.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/common")
public class CommonController {

    @Autowired
    private UserDao userDao;

    @RequestMapping("/index")
    public String index() {
        return "/index";
    }

    @RequestMapping("/one")
    public String adminTestPage(){
        return "/admin/adminTestPage1";
    }

    @RequestMapping("/two")
    public String adminTestPage2(){
        return "/admin/adminTestPage2";
    }

    @RequestMapping("/user")
    @ResponseBody
    public User findUserInfo(@RequestParam("id") Integer id){
        List<User> users = userDao.findUserById(id);
        User result = null;
        if (users.size() > 0){
            result = users.get(0);
        }
        return result;
    }
}
