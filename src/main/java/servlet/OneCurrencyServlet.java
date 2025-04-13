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
import util.ResponsePrintWriter;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/currency/*")

public class OneCurrencyServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String servletPathInfo = req.getPathInfo();
        boolean noCurrencyCode = servletPathInfo.length() == 1;

        if (noCurrencyCode) {
            ResponsePrintWriter.printResponse(resp,400, "text/html", "Код валюты отсутствует в адресе");
            return;
        }

        if (servletPathInfo.length() == 4) {
            String currencyCode = servletPathInfo.substring(1, 4).toUpperCase();
            CurrencyDto currencyDto = new CurrencyDto(currencyCode);
            OneCurrencyService oneCurrencyService = OneCurrencyService.getInstance();
            Currency currency = oneCurrencyService.createCurrency(currencyDto);

            if (currency != null) {
                String currencyJsonString = new Gson().toJson(currency);
                ResponsePrintWriter.printResponse(resp,200, "application/json", currencyJsonString);
            } else {
                ResponsePrintWriter.printResponse(resp,404, "text/html", "Валюта не найдена");
            }

        } else {
            ResponsePrintWriter.printResponse(resp,400, "text/html", "Некорректный код валюты");
        }
    }
}
