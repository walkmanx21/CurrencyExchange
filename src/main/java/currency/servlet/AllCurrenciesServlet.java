package currency.servlet;

import com.google.gson.Gson;
import currency.CurrencyDao;
import currency.dto.CurrencyRequestDto;
import currency.CurrencyService;
import currency.Currency;
import exception.AnyErrorException;
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

        boolean codeIsEmpty = code == null || code.isEmpty();
        boolean fullNameIsEmpty = fullName == null || fullName.isEmpty();
        boolean signIsEmpty = sign == null || sign.isEmpty();

        if (codeIsEmpty || fullNameIsEmpty || signIsEmpty) {
            ResponsePrintWriter.printResponse(resp,400, "Отсутствует нужное поле формы");
            return;
        }

        CurrencyRequestDto currencyRequestDto = new CurrencyRequestDto(code, fullName, sign);
        Currency currency = null;
        try {
            currency = currencyService.insertCurrency(currencyRequestDto);
        } catch (CurrencyAlreadyExistsException e) {
            ResponsePrintWriter.printResponse(resp, 409, "Валюта с указанным кодом уже существует");
        } catch (Throwable throwable) {
            ResponsePrintWriter.printResponse(resp, 500, "Ошибка");
        }
        String currencyJsonString = new Gson().toJson(currency);
        ResponsePrintWriter.printResponse(resp, 201, currencyJsonString);

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Currency> currencies = null;
        try {
            currencies = currencyDao.findAllCurrencies();
        } catch (AnyErrorException e) {
            ResponsePrintWriter.printResponse(resp, 500, "Ошибка");
        }
        String currenciesJsonString = new Gson().toJson(currencies);
        ResponsePrintWriter.printResponse(resp, 200, currenciesJsonString);
    }

}
