package cn.crabime.cas.controller;

import org.jasig.cas.client.util.CommonUtils;
import org.springframework.security.cas.authentication.CasAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Controller
@RequestMapping("/common")
public class CommonController {

    @RequestMapping("/")
    public String common(HttpServletRequest request) throws UnsupportedEncodingException {
        String targetUrl = "http://localhost:8085/cas-two/common/";
        final CasAuthenticationToken casAuthenticationToken = (CasAuthenticationToken) request.getUserPrincipal();
        final String proxyTicket = casAuthenticationToken.getAssertion().getPrincipal().getProxyTicketFor(targetUrl);
        System.out.println("proxy ticket为：" + proxyTicket);

        final String serviceUrl = targetUrl + "?ticket=" + URLEncoder.encode(proxyTicket, "UTF-8");
        String proxyResponse = CommonUtils.getResponseFromServer(serviceUrl, "UTF-8");
        return "common";
    }

    @RequestMapping("/hotel")
    public String hotel() {
        return "hotel";
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }
}
