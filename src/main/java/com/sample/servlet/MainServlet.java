package com.sample.servlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
//@TODO
//Разбить просто на фронте файлы - пост форма, папки - ссылка,
// ну и над визуальной частью поработать, добавить что-нибудь в "/"
// + отрефакторить
@WebServlet("/files")
public class MainServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.uuuu HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        req.setAttribute("currentDateTime", dtf.format(now));


        Path currRelativePath = Objects.isNull(req.getParameter("path"))
                ? Paths.get("/")
                : Paths.get(req.getParameter("path"));

        File directoryPath = new File(currRelativePath.toAbsolutePath().toString());
        req.setAttribute("pathNow", currRelativePath.toAbsolutePath().toString());
        if (directoryPath.isFile()) {
            ServletContext ctx = getServletContext();
            InputStream fis = Files.newInputStream(directoryPath.toPath());
            String mimeType = ctx.getMimeType(directoryPath.getAbsolutePath());
            resp.setContentType(mimeType != null? mimeType:"application/octet-stream");
            resp.setContentLength((int) directoryPath.length());
            resp.setHeader("Content-Disposition", "attachment; filename=\"" + directoryPath.getName() + "\"");

            ServletOutputStream os = resp.getOutputStream();
            byte[] bufferData = new byte[1024];
            int read=0;
            while((read = fis.read(bufferData))!= -1){
                os.write(bufferData, 0, read);
            }
            os.flush();
            os.close();
            fis.close();
        }
        File[] filesList = directoryPath.listFiles();
        req.setAttribute("upPath", directoryPath.getParent());
        req.setAttribute("filesList", filesList);

        req.getRequestDispatcher("mypage.jsp").forward(req, resp);

    }
}