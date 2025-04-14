package rate.servlet;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import rate.RateService;
import rate.dto.RateResponseDto;
import util.ResponsePrintWriter;

import java.io.IOException;
import java.util.List;


@WebServlet("/exchangeRates")
public class AllRatesServlet extends HttpServlet {

    private final RateService rateService = RateService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<RateResponseDto> rates = rateService.findAllExchangeRate();
        String ratesJsonString = new Gson().toJson(rates);

        ResponsePrintWriter.printResponse(resp, 200, ratesJsonString);
    }
}
