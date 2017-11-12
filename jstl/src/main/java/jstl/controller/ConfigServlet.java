package jstl.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

/**
 * Created by crabime on 10/31/17.
 */
public class ConfigServlet extends HttpServlet {
    private final static Logger logger = LoggerFactory.getLogger(ConfigServlet.class);

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        logger.warn("这里是启动文件");
    }
}
