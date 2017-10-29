package jstl.tags;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

/**
 * Created by crabime on 10/29/17.
 * 在页面中指定Num属性
 */
public class PageNumTag extends SimpleTagSupport {

    private int num;

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    @Override
    public void doTag() throws JspException, IOException {
        getJspContext().setAttribute("num", "当前页面号码为(来自request):" + num);
    }
}
