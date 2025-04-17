package currencyExchange;

import com.google.gson.Gson;
import currencyExchange.dto.ExchangeRequestDto;
import currencyExchange.dto.ExchangeResponseDto;
import exception.AnyErrorException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import util.ResponsePrintWriter;

import java.io.IOException;
import java.math.BigDecimal;

@WebServlet("/exchange")
public class CurrencyExchangeServlet extends HttpServlet {

    private final CurrencyExchangeService currencyExchangeService = CurrencyExchangeService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String baseCurrencyCode = req.getParameter("from");
        String targetCurrencyCode = req.getParameter("to");
        String amountString = req.getParameter("amount");
        if (amountString.contains(",")) {
            amountString = amountString.replace(',', '.');
        }
        BigDecimal amount = new BigDecimal(amountString);

        ExchangeRequestDto exchangeRequestDto = new ExchangeRequestDto(baseCurrencyCode, targetCurrencyCode, amount);
        ExchangeResponseDto exchangeResponseDto = null;

        try {
            exchangeResponseDto = currencyExchangeService.makeCurrencyExchange(exchangeRequestDto);
        } catch (AnyErrorException e) {
            ResponsePrintWriter.printResponse(resp, 500, "Ошибка");
        }

        if (exchangeResponseDto.getRate() == null) {
            ResponsePrintWriter.printResponse(resp, 404, "Валюта не найдена");
        } else {
            String exchangeRateJsonString = new Gson().toJson(exchangeResponseDto);
            ResponsePrintWriter.printResponse(resp, 200, exchangeRateJsonString);
        }
    }
}
