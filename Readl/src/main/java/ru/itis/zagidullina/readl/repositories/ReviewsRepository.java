package ru.itis.zagidullina.readl.repositories;

import ru.itis.zagidullina.readl.models.Review;

import java.util.List;

public interface ReviewsRepository {
    void save(Review review);
    List<Review> getReviewsOfBook(Integer id, Integer count);
}
