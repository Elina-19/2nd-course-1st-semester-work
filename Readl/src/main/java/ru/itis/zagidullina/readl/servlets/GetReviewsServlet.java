package ru.itis.zagidullina.readl.servlets;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.internal.bind.util.ISO8601Utils;
import ru.itis.zagidullina.readl.models.Review;
import ru.itis.zagidullina.readl.services.BookService;
import ru.itis.zagidullina.readl.services.RateService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/getReviews")
public class GetReviewsServlet extends HttpServlet {

    private RateService rateService;
    private Gson gson;

    @Override
    public void init(ServletConfig config) throws ServletException {
        rateService = (RateService) config.getServletContext().getAttribute("rateService");
        gson = new Gson();
    }

//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        JsonObject object = new JsonObject();
//        object.addProperty("name", "hey");
//        object.addProperty("comment", "hehfuwahfj");
//        System.out.println(object.getAsString());
//        response.getWriter().write(object.getAsString());
//    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JsonArray array = new JsonArray();

        Integer bookId = Integer.valueOf(request.getParameter("bookId"));
        Integer count = Integer.valueOf(request.getParameter("count"));

        List<Review> reviews = rateService.getReviewsOfBook(bookId, count);

        if (reviews != null) {
            for (Review review : reviews) {
                JsonObject object = new JsonObject();
                object.addProperty("id", review.getId());
                object.addProperty("nickname", review.getAccount().getNickname());
                object.addProperty("content", review.getContent());
                object.addProperty("date", review.getDate().toString());

                array.add(object);
            }
            String str = array.toString();

            response.setContentType("application/json");
            response.getWriter().write(str);
        }else response.getWriter().write("no reviews");
    }
}
