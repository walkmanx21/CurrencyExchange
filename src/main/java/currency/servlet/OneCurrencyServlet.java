package currency.servlet;

import com.google.gson.Gson;
import currency.dto.CurrencyRequestDto;
import currency.dto.CurrencyResponseDto;
import currency.CurrencyService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import util.ResponsePrintWriter;

import java.io.IOException;

@WebServlet("/currency/*")

public class OneCurrencyServlet extends HttpServlet {
    private final CurrencyService currencyService = CurrencyService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String servletPathInfo = req.getPathInfo();
        boolean noCurrencyCode = servletPathInfo.length() == 1;

        if (noCurrencyCode) {
            ResponsePrintWriter.printResponse(resp,400, "text/html", "Код валюты отсутствует в адресе");
            return;
        }

        if (servletPathInfo.length() == 4) {
            String currencyCode = servletPathInfo.substring(1, 4).toUpperCase();
            CurrencyRequestDto currencyRequestDto = new CurrencyRequestDto(currencyCode);
            CurrencyResponseDto currencyResponseDto = currencyService.findOneCurrency(currencyRequestDto);

            if (currencyResponseDto != null) {
                String currencyJsonString = new Gson().toJson(currencyResponseDto);
                ResponsePrintWriter.printResponse(resp,200, "application/json", currencyJsonString);
            } else {
                ResponsePrintWriter.printResponse(resp,404, "text/html", "Валюта не найдена");
            }

        } else {
            ResponsePrintWriter.printResponse(resp,400, "text/html", "Некорректный код валюты");
        }
    }
}
