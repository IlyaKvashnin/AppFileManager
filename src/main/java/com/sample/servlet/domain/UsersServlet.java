package com.sample.servlet.domain;

import com.sample.servlet.infrastructure.models.UserProfile;
import com.sample.servlet.infrastructure.services.AccountsService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
            resp.setContentType("text/html;charset=utf-8");
            resp.getWriter().println("Отсутсвует email, логин или пароль");
            return;
        }

        UserProfile profile = new UserProfile(login,pass,email);
        if (AccountsService.getUserByLogin(login)==null) {
            AccountsService.addNewUser(profile);

            req.getSession().setAttribute("login",login);
            req.getSession().setAttribute("pass",pass);
            req.getSession().setAttribute("email",email);

            // Создание новой папки для пользователя
            File folder = new File("/Users/ilya/fileManager/" + login);
            boolean isCreationSuccess = folder.mkdir();
            //Скорее всего никогда не будет false, но если будет нехватать памяти или что-то ещё, то сработает
            if (!isCreationSuccess) {
                resp.setContentType("text/html;charset=utf-8");
                resp.getWriter().println("Случилась ошибка при создании папки, попробуйте ещё раз");
                return;
            }
            resp.sendRedirect("/files");
        } else {
            resp.setContentType("text/html;charset=utf-8");
            resp.getWriter().println("Пользователь с таким логином уже есть в системе");
        }
    }
}
