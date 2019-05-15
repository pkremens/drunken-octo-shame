package org.jboss.qe.kremilek.jms;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author Petr Kremensky pkremens@redhat.com
 */
@WebServlet("/HelloServlet")
public class HelloServlet extends HttpServlet {
    static String PAGE_HEADER = "<html><head><title>helloworld</title></head><body>";

    static String PAGE_FOOTER = "</body></html>";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter writer = resp.getWriter();
        writer.println(PAGE_HEADER);
//        writer.println("<h1>" + helloService.createHelloMessage("World") + "</h1>");
        writer.println("<h1>Hello static World</h1>");
        writer.println(PAGE_FOOTER);
        writer.close();
    }

}
