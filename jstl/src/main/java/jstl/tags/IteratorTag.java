package jstl.tags;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.util.Collection;

/**
 * Created by crabime on 10/29/17.
 * 迭代标签
 */
public class IteratorTag extends SimpleTagSupport{

    private String collection;
    private String item;

    public String getCollection() {
        return collection;
    }

    public void setCollection(String collection) {
        this.collection = collection;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    @Override
    public void doTag() throws JspException, IOException {
        Collection items = (Collection)getJspContext().getAttribute(collection);
        for (Object i : items){
            getJspContext().setAttribute(item, i);
            //输出标签体
            getJspBody().invoke(null);
        }
    }
}
