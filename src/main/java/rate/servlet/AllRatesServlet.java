package rate.servlet;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import rate.Rate;
import rate.RateService;
import rate.dto.RateRequestDto;
import rate.dto.RateResponseDto;
import util.ResponsePrintWriter;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;


@WebServlet("/exchangeRates")
public class AllRatesServlet extends HttpServlet {

    private final RateService rateService = RateService.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String baseCurrencyCode = req.getParameter("baseCurrencyCode");
        String targetCurrencyCode = req.getParameter("targetCurrencyCode");
        String rateString = req.getParameter("rate");
        if (rateString.contains(",")) {
            rateString = rateString.replace(',', '.');
        }
        BigDecimal rate =  new BigDecimal(rateString);

        RateRequestDto rateRequestDto = new RateRequestDto(baseCurrencyCode, targetCurrencyCode, rate);
        RateResponseDto rateResponseDto = rateService.insertNewExchangeRate(rateRequestDto);


        System.out.println();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<RateResponseDto> rates = rateService.findAllExchangeRate();
        String ratesJsonString = new Gson().toJson(rates);

        ResponsePrintWriter.printResponse(resp, 200, ratesJsonString);
    }
}
