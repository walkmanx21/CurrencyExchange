package rate.servlet;

import com.google.gson.Gson;
import exception.ExchangeRateNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Getter;

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
import java.math.BigDecimal;


@WebServlet("/exchangeRate/*")
public class OneRateServlet extends HttpServlet {

    private RateService rateService = RateService.getInstance();

    @Override
    protected void doPatch(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PathInfo pathInfo = getPathInfo(req);
        if (pathInfo == null) {
            ResponsePrintWriter.printResponse(resp, 400, "Коды валют пары отсутствуют в адресе");
            return;
        }
        String rateString = req.getParameter("rate");
        if (rateString.contains(",")) {
            rateString = rateString.replace(',', '.');
        }
        BigDecimal rate =  new BigDecimal(rateString);
        RateRequestDto rateRequestDto = new RateRequestDto(pathInfo.getBaseCurrencyCode(), pathInfo.getTargetCurrencyCode(), rate);

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        PathInfo pathInfo = getPathInfo(req);
        if (pathInfo == null) {
            ResponsePrintWriter.printResponse(resp, 400, "Коды валют пары отсутствуют в адресе");
            return;
        }
        RateRequestDto rateRequestDto = new RateRequestDto(pathInfo.getBaseCurrencyCode(), pathInfo.getTargetCurrencyCode());
        RateResponseDto rateResponseDto = null;

        try {
            rateResponseDto = rateService.findOneExchangeRate(rateRequestDto);
        } catch (ExchangeRateNotFoundException e) {
            ResponsePrintWriter.printResponse(resp, 404, "Обменный курс для пары не найден");
        }

        String currencyJsonString = new Gson().toJson(rateResponseDto);
        ResponsePrintWriter.printResponse(resp, 200, currencyJsonString);

    }

    @Getter
    @AllArgsConstructor
    static
    class PathInfo {
        String baseCurrencyCode;
        String targetCurrencyCode;
    }

    private PathInfo getPathInfo (HttpServletRequest req) {
        String servletPathInfo = req.getPathInfo();
        boolean noCurrenciesCode = servletPathInfo.length() == 1;
        if (noCurrenciesCode) {
            return null;
        }
        String baseCurrencyCode = servletPathInfo.substring(1, 4).toUpperCase();
        String targetCurrencyCode = servletPathInfo.substring(4, 7).toUpperCase();
        return new PathInfo(baseCurrencyCode, targetCurrencyCode);

    }

}
