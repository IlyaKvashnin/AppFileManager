package com.sample.servlet.helpers;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class HtmlHelper {
    public static void CreateAlert(String message, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html;charset=utf-8");
        resp.getWriter().println("<script type=\"text/javascript\">");
        resp.getWriter().println("alert('" + message + "');");
        resp.getWriter().println("location='login.jsp';");
        resp.getWriter().println("</script>");
    }
}
