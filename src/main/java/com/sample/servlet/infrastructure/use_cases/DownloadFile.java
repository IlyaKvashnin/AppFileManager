package com.sample.servlet.infrastructure.use_cases;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

public class DownloadFile {
    public void download(HttpServletResponse resp, ServletContext ctx, File currentFile) throws IOException {
        InputStream fis = Files.newInputStream(currentFile.toPath());

        String mimeType = ctx.getMimeType(currentFile.toString());

        resp.setContentType(mimeType != null ? mimeType : "application/octet-stream");
        resp.setContentLength((int) currentFile.length());
        resp.setHeader("Content-Disposition", "attachment; filename=\"" + currentFile.getName() + "\"");

        try (ServletOutputStream os = resp.getOutputStream()) {
            byte[] bufferData = new byte[1024];
            int read = 0;

            while ((read = fis.read(bufferData)) != -1) {
                os.write(bufferData, 0, read);
            }

            fis.close();
        }
    }
}
