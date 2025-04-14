package rate.servlet;

import com.google.gson.Gson;
import rate.dto.RateRequestDto;
import rate.dto.RateResponseDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import rate.RateService;
import util.ResponsePrintWriter;

import java.io.IOException;


@WebServlet("/exchangeRate/*")
public class OneRateServlet extends HttpServlet {

    private RateService rateService = RateService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String servletPathInfo = req.getPathInfo();
        boolean noCurrenciesCode = servletPathInfo.length() == 1;

        if (noCurrenciesCode) {
            ResponsePrintWriter.printResponse(resp, 400, "Коды валют пары отсутствуют в адресе");
            return;
        }

        String baseCurrencyCode = servletPathInfo.substring(1, 4).toUpperCase();
        String targetCurrencyCode = servletPathInfo.substring(4, 7).toUpperCase();
        RateRequestDto rateRequestDto = new RateRequestDto(baseCurrencyCode, targetCurrencyCode);
        RateResponseDto rateResponseDto = rateService.findOneExchangeRate(rateRequestDto);
        String currencyJsonString = new Gson().toJson(rateResponseDto);
        ResponsePrintWriter.printResponse(resp, 200, currencyJsonString);

    }
}
