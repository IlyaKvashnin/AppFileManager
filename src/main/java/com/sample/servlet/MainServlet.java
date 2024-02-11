package com.sample.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@WebServlet("/")
public class MainServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setAttribute("name", "Ilya");
        Path currRelativePath = Paths.get("/");
        File directoryPath = new File(currRelativePath.toAbsolutePath().toString());
        File[] filesList = directoryPath.listFiles();
        req.setAttribute("filesList", filesList);

        req.getRequestDispatcher("mypage.jsp").forward(req, resp);

    }
}