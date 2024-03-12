package com.sample.servlet.domain;

import com.sample.servlet.infrastructure.use_cases.DownloadFile;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

@WebServlet("/download")
public class DownloadServlet extends HttpServlet {
    final String REQUEST_PARAMETER = "path";
    private final DownloadFile downloadFile;

    public DownloadServlet() {
        super();
        this.downloadFile = new DownloadFile();
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
