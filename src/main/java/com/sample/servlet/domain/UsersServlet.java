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
import java.io.File;
import java.io.IOException;

@WebServlet("/registration")
public class UsersServlet extends HttpServlet {
    public void doGet(HttpServletRequest req,
                      HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("registration.jsp").forward(req, resp);
    }

    public void doPost(HttpServletRequest req,
                       HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String login = req.getParameter("login");
        String pass = req.getParameter("pass");

        if (email.isEmpty() || login.isEmpty() || pass.isEmpty()) {
            HtmlHelper.CreateAlert("Все поля должны быть обязательно заполнены", resp);

            return;
        }

        UserService accountsService = new UserService();
        try {
            accountsService.addUser(login, pass, email);

            req.getSession().setAttribute("login", login);
            req.getSession().setAttribute("pass", pass);
            req.getSession().setAttribute("email", email);

            File folder = new File("/Users/ilya/fileManager/" + login);
            boolean isCreationSuccess = folder.mkdir();

            if (!isCreationSuccess) {
                HtmlHelper.CreateAlert("Случилась ошибка при создании папки, попробуйте ещё раз", resp);

                return;
            }

            resp.sendRedirect("/files?=path/Users/ilya/fileManager/" + login);
        } catch (IllegalArgumentException e) {
            HtmlHelper.CreateAlert("Пользователь с таким логином уже есть в системе", resp);
        }
    }
}
