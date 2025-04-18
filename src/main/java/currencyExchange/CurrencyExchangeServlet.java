package currencyExchange;

import com.google.gson.Gson;
import currencyExchange.dto.ExchangeRequestDto;
import currencyExchange.dto.ExchangeResponseDto;
import exception.AnyErrorException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import util.CheckDecimalSeparator;
import util.ResponsePrintWriter;
import java.math.BigDecimal;

@WebServlet("/exchange")
public class CurrencyExchangeServlet extends HttpServlet {
    private final CurrencyExchangeService currencyExchangeService = CurrencyExchangeService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        String baseCurrencyCode;
        String targetCurrencyCode;
        String amountString;

        try {
            baseCurrencyCode = req.getParameter("from").toUpperCase();
            targetCurrencyCode = req.getParameter("to").toUpperCase();
            amountString = req.getParameter("amount");
        } catch (Exception e) {
            ResponsePrintWriter.printResponse(resp, 400, "Поле формы не заполнено");
            return;
        }

        boolean baseCurrencyCodeIsEmpty = baseCurrencyCode.isEmpty();
        boolean targetCurrencyCodeIsEmpty = targetCurrencyCode.isEmpty();
        boolean amountStringIsEmpty = amountString == null || amountString.isEmpty();

        if (baseCurrencyCodeIsEmpty || targetCurrencyCodeIsEmpty || amountStringIsEmpty) {
            ResponsePrintWriter.printResponse(resp, 400, "Отсутствует нужное поле формы");
            return;
        }

        amountString = CheckDecimalSeparator.correction(amountString);
        BigDecimal amount;

        try {
            amount = new BigDecimal(amountString);
        } catch (NumberFormatException e) {
            ResponsePrintWriter.printResponse(resp, 400, "В поле amount введено некорректное число");
            return;
        }

        ExchangeRequestDto exchangeRequestDto = new ExchangeRequestDto(baseCurrencyCode, targetCurrencyCode, amount);
        ExchangeResponseDto exchangeResponseDto;

        try {
            exchangeResponseDto = currencyExchangeService.makeCurrencyExchange(exchangeRequestDto);
        } catch (AnyErrorException e) {
            ResponsePrintWriter.printResponse(resp, 500, "Ошибка");
            return;
        }

        if (exchangeResponseDto.getRate() == null) {
            ResponsePrintWriter.printResponse(resp, 404, "Валюта не найдена");
        } else {
            String exchangeRateJsonString = new Gson().toJson(exchangeResponseDto);
            ResponsePrintWriter.printResponse(resp, 200, exchangeRateJsonString);
        }
    }
}
