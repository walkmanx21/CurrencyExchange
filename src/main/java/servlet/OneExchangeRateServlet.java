package servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;


@WebServlet("/exchangeRate/*")
public class OneExchangeRateServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String servletPathInfo = req.getPathInfo();
        String baseCurrencyCode = servletPathInfo.substring(1, 4).toUpperCase();
        String targetCurrencyCode = servletPathInfo.substring(4, 7).toUpperCase();

        System.out.println();
    }
}
