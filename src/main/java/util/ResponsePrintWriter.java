package util;

import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

public final class ResponsePrintWriter {
    private ResponsePrintWriter() {
    }

    public static void printResponse (HttpServletResponse resp, int status, String message) {
        try (PrintWriter out = resp.getWriter()) {
            resp.setStatus(status);
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            out.print(message);
            out.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
