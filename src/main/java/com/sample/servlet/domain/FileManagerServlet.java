package com.sample.servlet.domain;

import com.sample.servlet.infrastructure.models.UserProfile;
import com.sample.servlet.infrastructure.services.AccountsService;
import com.sample.servlet.infrastructure.use_cases.DownloadFile;
import com.sample.servlet.infrastructure.use_cases.GetCurrentTime;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@WebServlet("/files")
public class FileManagerServlet extends HttpServlet {
    final String FILE_ATTRIBUTE = "creationTime";
    final String DATE_PATTERN = "MM/dd/yyyy HH:mm:ss";
    private final GetCurrentTime getCurrentTime;
    final String REQUEST_PARAMETER = "path";

    public FileManagerServlet() {
        super();
        this.getCurrentTime = new GetCurrentTime();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String login = (String)req.getSession().getAttribute("login");
        String pass = (String)req.getSession().getAttribute("pass");

        if (login == null || pass == null) {
            resp.sendRedirect("/login");
            return;
        }

        if (AccountsService.getUserByLogin(login)==null || !AccountsService.getUserByLogin(login).getPass().equals(pass)) {
            resp.sendRedirect("/login");
            return;
        }

        String currUserAbsolute = "/Users/ilya/fileManager/" + login;

        Path currUserAbsolutePath = Paths.get(currUserAbsolute);
        Path currRelativePath = Objects.isNull(req.getParameter(REQUEST_PARAMETER))
                ? currUserAbsolutePath
                : Paths.get(req.getParameter(REQUEST_PARAMETER));

        String currAbsolutePath = !currRelativePath.startsWith(currUserAbsolutePath)
                ? currUserAbsolutePath.toAbsolutePath().toString()
                : currRelativePath.toAbsolutePath().toString();

        File currDirectory = new File(currAbsolutePath);
        File[] listOfFiles = currDirectory.listFiles();

        try {
            FileTime creationTime = (FileTime) Files.getAttribute(currRelativePath, FILE_ATTRIBUTE);
            req.setAttribute(
                    FILE_ATTRIBUTE,
                    creationTime.toInstant()
                            .atZone(ZoneId.systemDefault())
                            .toLocalDateTime()
                            .format(DateTimeFormatter.ofPattern(DATE_PATTERN))
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        req.setAttribute("pathToUp", currDirectory.getParent());
        req.setAttribute("listOfFiles", listOfFiles);
        req.setAttribute("currentTime", getCurrentTime.get(DATE_PATTERN));
        req.setAttribute("currentPath", currAbsolutePath);

        req.getRequestDispatcher("main.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.getSession().removeAttribute("login");
        req.getSession().removeAttribute("pass");
        req.getSession().removeAttribute("email");

        resp.sendRedirect("/");
    }
}