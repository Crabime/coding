package jstl.tags;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.util.Date;

/**
 * Created by crabime on 10/29/17.
 * 自定义tag标签
 * 简单不带属性
 */
public class HelloWorldTag extends SimpleTagSupport {
    @Override
    public void doTag() throws JspException, IOException {
        getJspContext().getOut().write("hello world" + new Date());
    }
}
