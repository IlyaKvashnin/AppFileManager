package com.sample.servlet.domain;

import com.sample.servlet.helpers.HtmlHelper;
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
        String login = (String)req.getSession().getAttribute("login");
        String pass = (String)req.getSession().getAttribute("pass");
        String email = (String)req.getSession().getAttribute("email");

        if (login == null || pass == null || email == null) {
            req.getRequestDispatcher("login.jsp").forward(req, resp);
        }

        if (AccountsService.getUserByLogin(login)==null) {
            AccountsService.addNewUser(new UserProfile(login,pass,email));
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

        UserProfile profile = AccountsService.getUserByLogin(login);
        if (profile == null || !profile.getPass().equals(pass)) {
            HtmlHelper.CreateAlert("Неправильный логин или пароль", resp);

            return;
        }

        req.getSession().setAttribute("login",login);
        req.getSession().setAttribute("pass",pass);

        resp.sendRedirect("/files?path=/Users/ilya/fileManager/" + login);
    }
}
