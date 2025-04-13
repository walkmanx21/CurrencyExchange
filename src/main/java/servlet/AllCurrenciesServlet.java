package servlet;

import com.google.gson.Gson;
import dao.CurrencyDao;
import entity.Currency;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

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
        print(resp, 200, "application/json", currenciesJsonString);
    }

    private void print (HttpServletResponse resp, int status, String contentType, String message) throws IOException {
        PrintWriter out = resp.getWriter();
        resp.setStatus(status);
        resp.setContentType(contentType);
        resp.setCharacterEncoding("UTF-8");
        out.print(message);
        out.flush();
    }
}
