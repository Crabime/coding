package cn.crabime.cas.controller;

import org.springframework.security.cas.authentication.CasAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Random;

@Controller
@RequestMapping("/common")
public class CommonController {

    @RequestMapping("/")
    public String common(HttpServletRequest request) throws UnsupportedEncodingException {
        String targetUrl = "http://localhost:8085/cas-one/common/hotel/";
        final CasAuthenticationToken casAuthenticationToken = (CasAuthenticationToken) request.getUserPrincipal();
        final String proxyTicket = casAuthenticationToken.getAssertion().getPrincipal().getProxyTicketFor(targetUrl);
        System.out.println("CAS two -> CAS one proxy ticket为：" + proxyTicket);

        final String serviceUrl = targetUrl + "?ticket=" + URLEncoder.encode(proxyTicket, "UTF-8");
//        String proxyResponse = CommonUtils.getResponseFromServer(serviceUrl, "UTF-8");
        return "common";
    }

    @RequestMapping("/random")
    @ResponseBody
    private String getRandomString() {
        Random random = new Random();
        Double result = random.nextDouble();
        return "return a new String" + result.toString();
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }
}
