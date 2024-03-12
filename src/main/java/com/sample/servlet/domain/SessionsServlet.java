package com.sample.servlet.domain;

import com.sample.servlet.infrastructure.models.UserProfile;

import com.sample.servlet.infrastructure.services.AccountsService;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/")
public class SessionsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String login = req.getParameter("login");
        String pass = req.getParameter("pass");

        if (login.isEmpty() || pass.isEmpty()) {
            resp.setContentType("text/html;charset=utf-8");
            resp.getWriter().println("Отсутсвует логин или пароль");
            return;
        }

        UserProfile profile = AccountsService.getUserByLogin(login);
        if (profile == null || !profile.getPass().equals(pass)) {
            resp.setContentType("text/html;charset=utf-8");
            resp.getWriter().println("Неправильный логин или пароль");
            return;
        }

        req.getSession().setAttribute("login",login);
        req.getSession().setAttribute("pass",pass);

        //String currentURL = req.getRequestURL().toString();
        resp.sendRedirect("/files");
    }
}
