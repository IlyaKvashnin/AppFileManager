package com.sample.servlet.domain;

import com.sample.servlet.helpers.HtmlHelper;
import com.sample.servlet.infrastructure.models.User;

import com.sample.servlet.infrastructure.services.UserService;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/")
public class SessionsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String login = (String)req.getSession().getAttribute("login");
        String pass = (String)req.getSession().getAttribute("pass");

        UserService userService = new UserService();
        if (!userService.validUser(login, pass)) {
            req.getRequestDispatcher("login.jsp").forward(req, resp);
            return;
        }

        resp.sendRedirect("/files?path=/Users/ilya/fileManager/" + login);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String login = req.getParameter("login");
        String pass = req.getParameter("pass");

        if (login.isEmpty() || pass.isEmpty()) {
            HtmlHelper.CreateAlert("Отсуствует логин или пароль", resp);

            return;
        }

        UserService userService = new UserService();
        if (userService.validUser(login, pass)) {
            req.getSession().setAttribute("login", login);
            req.getSession().setAttribute("pass", pass);

            resp.sendRedirect("/files?path=/Users/ilya/fileManager/" + login);

        } else {
            HtmlHelper.CreateAlert("Неправильный логин или пароль", resp);
        }
    }
}
