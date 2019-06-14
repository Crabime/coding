package cn.crabime.cas.controller;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by crabime on 6/4/18.
 */
@Controller
@RequestMapping("/login")
public class LoginController {

    @RequestMapping("/a")
    public ModelAndView login(HttpServletRequest request) {
        ModelAndView model = new ModelAndView("login");
        String error = request.getParameter("error");
        if (!StringUtils.isEmpty(error)) {
            model.addObject("error", true);
        }
        return model;
    }
}
