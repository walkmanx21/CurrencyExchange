package servlet;

import dto.RequestCurrencyDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.OneCurrencyService;
import entity.*;

import java.io.IOException;

@WebServlet("/currency/EUR")
public class OneCurrencyServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String servletPath = req.getServletPath();
        String currencyCode = servletPath.substring(10, 13);
        RequestCurrencyDto requestCurrencyDto = new RequestCurrencyDto(currencyCode);
        OneCurrencyService oneCurrencyService = OneCurrencyService.getInstance();
        oneCurrencyService.createCurrency(requestCurrencyDto);
    }
}
