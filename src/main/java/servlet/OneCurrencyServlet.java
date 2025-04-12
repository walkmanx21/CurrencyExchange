package servlet;

import com.google.gson.Gson;
import dto.RequestCurrencyDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.OneCurrencyService;
import entity.*;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = {"/currency/USD", "/currency/EUR", "/currency/JPY", "/currency/RUB", "/currency/CHF"})

public class OneCurrencyServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String servletPath = req.getServletPath();
        String currencyCode = servletPath.substring(10, 13);
        RequestCurrencyDto requestCurrencyDto = new RequestCurrencyDto(currencyCode);
        OneCurrencyService oneCurrencyService = OneCurrencyService.getInstance();
        Currency currency = oneCurrencyService.createCurrency(requestCurrencyDto);

        String currencyJsonString = new Gson().toJson(currency);

        PrintWriter out = resp.getWriter();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        out.print(currencyJsonString);
        out.flush();

    }
}
