package com.sample.servlet.domain;

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

//@TODO
// добавить что-нибудь в "/"
// + отрефакторить
@WebServlet("/files")
public class FileManagerServlet extends HttpServlet {
    final String REQUEST_PARAMETER = "path";
    final String FILE_ATTRIBUTE = "creationTime";
    final String DATE_PATTERN = "MM/dd/yyyy HH:mm:ss";
    private final GetCurrentTime getCurrentTime;
    private final DownloadFile downloadFile;

    public FileManagerServlet() {
        super();
        this.getCurrentTime = new GetCurrentTime();
        this.downloadFile = new DownloadFile();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Path currRelativePath = Objects.isNull(req.getParameter(REQUEST_PARAMETER))
                ? Paths.get("/")
                : Paths.get(req.getParameter(REQUEST_PARAMETER));

        String currAbsolutePath = currRelativePath.toAbsolutePath().toString();

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
        Path currRelativePath = Objects.isNull(req.getParameter(REQUEST_PARAMETER))
                ? Paths.get("/")
                : Paths.get(req.getParameter(REQUEST_PARAMETER));

        String currAbsolutePath = currRelativePath.toAbsolutePath().toString();

        File currFile = new File(currAbsolutePath);

        ServletContext ctx = getServletContext();

        downloadFile.download(resp, ctx, currFile);
    }
}