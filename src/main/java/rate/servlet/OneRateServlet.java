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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;


@WebServlet("/exchangeRate/*")
public class OneRateServlet extends HttpServlet {

    private final RateService rateService = RateService.getInstance();

    @Override
    protected void doPatch(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PathInfo pathInfo = getPathInfo(req);
        if (pathInfo == null) {
            ResponsePrintWriter.printResponse(resp, 400, "Коды валют пары отсутствуют в адресе");
            return;
        }

        BufferedReader br = new BufferedReader(new InputStreamReader(req.getInputStream()));
        String rateString = br.readLine();
        if (rateString == null) {
            ResponsePrintWriter.printResponse(resp, 400, "Отсутствует нужное поле формы");
            return;
        }
        rateString = rateString.replace("rate=", "");
        if (rateString.isEmpty()) {
            ResponsePrintWriter.printResponse(resp, 400, "Нужное поле формы не заполнено");
            return;
        }
        if (rateString.contains("%2C")) {
            rateString = rateString.replace("%2C", ".");
        }
        BigDecimal rate =  new BigDecimal(rateString);
        RateRequestDto rateRequestDto = new RateRequestDto(pathInfo.getBaseCurrencyCode(), pathInfo.getTargetCurrencyCode(), rate);
        try {
            RateResponseDto rateResponseDto = rateService.updateExchangeRate(rateRequestDto);
            String rateJsonString = new Gson().toJson(rateResponseDto);
            ResponsePrintWriter.printResponse(resp, 200, rateJsonString);
        } catch (ExchangeRateNotFoundException e) {
            ResponsePrintWriter.printResponse(resp, 404, "Валютная пара отсутствует в базе данных");
        }
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
    private static class PathInfo {
        private String baseCurrencyCode;
        private String targetCurrencyCode;
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
