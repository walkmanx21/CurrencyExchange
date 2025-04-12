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

//@WebServlet(urlPatterns = {"/currency/USD", "/currency/EUR", "/currency/JPY", "/currency/RUB", "/currency/CHF"})
@WebServlet("/currency/*")

public class OneCurrencyServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String servletPathInfo = req.getPathInfo();
        String currencyCode = servletPathInfo.substring(1, 4);

        CurrencyDto currencyDto = new CurrencyDto(currencyCode);
        OneCurrencyService oneCurrencyService = OneCurrencyService.getInstance();
        Currency currency = oneCurrencyService.createCurrency(currencyDto);

        String currencyJsonString = new Gson().toJson(currency);

        PrintWriter out = resp.getWriter();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        out.print(currencyJsonString);
        out.flush();

    }
}
