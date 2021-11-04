package ru.itis.zagidullina.readl.services;

import ru.itis.zagidullina.readl.models.Review;

import java.util.List;

public interface RateService {
    void saveReview(Review review);
    List<Review> getReviewsOfBook(Integer bookId, Integer count);
}
