package servlet;

import com.google.gson.Gson;
import dao.CurrencyDao;
import entity.Currency;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import util.ResponsePrintWriter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/currencies")
public class AllCurrenciesServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        CurrencyDao currencyDao = CurrencyDao.getInstance();
        List<Currency> currencies = currencyDao.findAllEntities();

        String currenciesJsonString = new Gson().toJson(currencies);
        ResponsePrintWriter.printResponse(resp, 200, "application/json", currenciesJsonString);
    }

}
