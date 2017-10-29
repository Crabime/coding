package jstl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by crabime on 10/28/17.
 * 使用foreach循环遍历
 */
@WebServlet(name = "list", urlPatterns = "/list")
public class ForLoopServlet extends HttpServlet {
    @Override
    public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        RequestDispatcher rd = null;
        List<String> catagory = new ArrayList<String>();
        catagory.add("clothes");
        catagory.add("food");
        request.setAttribute("catagory", catagory);
        rd = request.getRequestDispatcher("/basic-label.jsp");
        rd.forward(req, res);
    }
}
