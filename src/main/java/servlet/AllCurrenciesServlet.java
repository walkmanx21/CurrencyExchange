package servlet;

import com.google.gson.Gson;
import dao.CurrencyDao;
import dto.CurrencyDto;
import entity.Currency;
import exception.CurrencyAlreadyExistsException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.CurrencyService;
import util.ResponsePrintWriter;

import java.io.IOException;
import java.util.List;

@WebServlet("/currencies")
public class AllCurrenciesServlet extends HttpServlet {
    private final CurrencyDao currencyDao = CurrencyDao.getInstance();
    private final CurrencyService currencyService = CurrencyService.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String code = req.getParameter("code");
        String fullName = req.getParameter("name");
        String sign = req.getParameter("sign");

        CurrencyDto currencyDto = new CurrencyDto(code, fullName, sign);
        Currency currency = null;
        try {
            currency = currencyService.insertCurrency(currencyDto);
        } catch (CurrencyAlreadyExistsException e) {
            ResponsePrintWriter.printResponse(resp, 409, "application/json", "Валюта с указанным кодом уже существует");
        }
        String currencyJsonString = new Gson().toJson(currency);
        ResponsePrintWriter.printResponse(resp, 201, "application/json", currencyJsonString);

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Currency> currencies = currencyDao.findAllEntities();
        String currenciesJsonString = new Gson().toJson(currencies);
        ResponsePrintWriter.printResponse(resp, 200, "application/json", currenciesJsonString);
    }

}
