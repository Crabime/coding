package cn.crabime.session.basic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "SessionServlet", urlPatterns = "/session")
public class SessionServlet extends HttpServlet {

    private final static Logger logger = LoggerFactory.getLogger(SessionServlet.class);

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        this.doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        HttpSession session = request.getSession();
        String sessionID = session.getId();
        logger.info("当前sessionId={}", sessionID);
        String remoteHost = request.getRemoteHost();
        int remotePort = request.getRemotePort();
        logger.info("请求到这里了，remoteHost={}，remotePort={}", remoteHost, remotePort);

        String name = (String)session.getAttribute("名字");
        int age = (int) session.getAttribute("age");
        PrintWriter writer = null;
        try {
            writer = response.getWriter();
            writer.append("Current Server session ID = ").append(sessionID).append("\n")
                    .append("name = ").append(name).append("\n")
                    .append("age = ").append(Integer.toString(age));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                writer.close();
            }
            writer = null;
        }
    }
}
