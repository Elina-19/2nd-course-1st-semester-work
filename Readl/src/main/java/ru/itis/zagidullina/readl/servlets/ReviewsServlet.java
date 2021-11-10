package ru.itis.zagidullina.readl.servlets;

import ru.itis.zagidullina.readl.filters.AccessFilter;
import ru.itis.zagidullina.readl.models.Account;
import ru.itis.zagidullina.readl.models.Book;
import ru.itis.zagidullina.readl.models.Review;
import ru.itis.zagidullina.readl.repositories.BookRepository;
import ru.itis.zagidullina.readl.services.BookService;
import ru.itis.zagidullina.readl.services.RateService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/reviews")
public class ReviewsServlet extends HttpServlet {

    private RateService rateService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        rateService = (RateService) config.getServletContext().getAttribute("rateService");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("bookId", Integer.valueOf(request.getParameter("id")));
        request.getRequestDispatcher("/WEB-INF/jsp/reviews.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute("account");

        Review review = Review.builder()
                .account(account)
                .book(Book.builder()
                        .id(Integer.valueOf(request.getParameter("bookId")))
                        .build())
                .content(request.getParameter("content"))
                .build();

        rateService.saveReview(review);
    }
}
