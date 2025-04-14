package servlet;

import dto.RateRequestDto;
import dto.RateResponseDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.RateService;

import java.io.IOException;


@WebServlet("/exchangeRate/*")
public class OneRateServlet extends HttpServlet {

    private RateService rateService = RateService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String servletPathInfo = req.getPathInfo();
        String baseCurrencyCode = servletPathInfo.substring(1, 4).toUpperCase();
        String targetCurrencyCode = servletPathInfo.substring(4, 7).toUpperCase();
        RateRequestDto rateRequestDto = new RateRequestDto(baseCurrencyCode, targetCurrencyCode);
        RateResponseDto rateResponseDto = rateService.findOneExchangeRate(rateRequestDto);

        System.out.println();
    }
}
