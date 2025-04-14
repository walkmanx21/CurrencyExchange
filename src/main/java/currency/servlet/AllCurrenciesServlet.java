package currency.servlet;

import com.google.gson.Gson;
import currency.CurrencyDao;
import currency.dto.CurrencyRequestDto;
import currency.dto.CurrencyResponseDto;
import currency.CurrencyService;
import currency.Currency;
import exception.CurrencyAlreadyExistsException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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

        CurrencyRequestDto currencyRequestDto = new CurrencyRequestDto(code, fullName, sign);
        Currency currency = null;
        try {
            currency = currencyService.insertCurrency(currencyRequestDto);
        } catch (CurrencyAlreadyExistsException e) {
            ResponsePrintWriter.printResponse(resp, 409, "Валюта с указанным кодом уже существует");
        }
        String currencyJsonString = new Gson().toJson(currency);
        ResponsePrintWriter.printResponse(resp, 201, currencyJsonString);

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Currency> currencies = currencyDao.findAllCurrencies();
        String currenciesJsonString = new Gson().toJson(currencies);
        ResponsePrintWriter.printResponse(resp, 200, currenciesJsonString);
    }

}
