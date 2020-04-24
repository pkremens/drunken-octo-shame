package cz.redhat.pkremens; /**
 * @author Petr Kremensky pkremens@redhat.com
 */

import org.eclipse.microprofile.config.ConfigProvider;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.stream.Collectors;

public class Applicant extends HttpServlet {

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        System.out.println("-------------------------------------");
        System.out.println("Servlet context name: " + getServletContext().getServletContextName());
        System.out.println("Servlet virtual server name: " + getServletContext().getVirtualServerName());
        System.out.println("Servlet name from servlet config: " + getServletConfig().getServletName());
        System.out.println("Servlet name: " + getServletName());
        System.out.println("Servlet info: " + getServletInfo());
        ConfigProvider.getConfig().getConfigSources().forEach(configSource -> {
            System.out.println("=================================");
            System.out.println(configSource.getName());
            System.out.println(configSource.getOrdinal());
            System.out.println("Property names: " + configSource.getPropertyNames().stream().collect(Collectors.joining(", ")));
        });
        System.out.println("-------------------------------------");

        String email
                = getServletConfig()
                .getInitParameter("Email");
        String website
                = getServletContext()
                .getInitParameter("Website-name");
        PrintWriter out = response.getWriter();
        out.println("<center><h1>" + website
                + "</h1></center><br><p>Contact us:"
                + email);
    }
}

