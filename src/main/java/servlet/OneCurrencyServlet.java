package servlet;

import com.google.gson.Gson;
import dto.CurrencyDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.OneCurrencyService;
import entity.*;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/currency/*")

public class OneCurrencyServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        String servletPathInfo = req.getPathInfo();
        boolean noCode = servletPathInfo.length() == 1;

        if (noCode) {
            resp.setStatus(400);
            resp.setContentType("text/html");
            resp.setCharacterEncoding("UTF-8");
            out.print("Код валюты отсутствует в адресе");
            return;
        }

        if (servletPathInfo.length() == 4) {
            String currencyCode = servletPathInfo.substring(1, 4).toUpperCase();
            CurrencyDto currencyDto = new CurrencyDto(currencyCode);
            OneCurrencyService oneCurrencyService = OneCurrencyService.getInstance();
            Currency currency = oneCurrencyService.createCurrency(currencyDto);

            if (currency != null) {
                resp.setStatus(200);

                String currencyJsonString = new Gson().toJson(currency);
                resp.setContentType("application/json");
                resp.setCharacterEncoding("UTF-8");
                out.print(currencyJsonString);
                out.flush();
            } else {
                resp.setStatus(404);
                resp.setContentType("text/html");
                resp.setCharacterEncoding("UTF-8");
                out.print("Валюта не найдена");
                out.flush();
            }
        } else {
            resp.setStatus(400);
            resp.setContentType("text/html");
            resp.setCharacterEncoding("UTF-8");
            out.print("Некорректный код валюты");
        }

    }
}
