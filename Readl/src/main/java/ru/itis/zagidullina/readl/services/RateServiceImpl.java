package ru.itis.zagidullina.readl.services;

import ru.itis.zagidullina.readl.models.Review;
import ru.itis.zagidullina.readl.repositories.ReviewsRepository;

import java.util.List;

public class RateServiceImpl implements RateService {

    private ReviewsRepository reviewsRepository;

    public RateServiceImpl(ReviewsRepository reviewsRepository){
        this.reviewsRepository = reviewsRepository;
    }

    @Override
    public void saveReview(Review review) {
        reviewsRepository.save(review);
    }

    @Override
    public List<Review> getReviewsOfBook(Integer bookId, Integer count) {
        List<Review> reviews = reviewsRepository.getReviewsOfBook(bookId, count);

        if (reviews.size() == 0){
            return null;
        }
        else return reviews;
    }
}
