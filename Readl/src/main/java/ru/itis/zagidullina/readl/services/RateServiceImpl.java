package ru.itis.zagidullina.readl.services;

import ru.itis.zagidullina.readl.models.Account;
import ru.itis.zagidullina.readl.models.Review;
import ru.itis.zagidullina.readl.repositories.AccountsRepository;
import ru.itis.zagidullina.readl.repositories.ReviewsRepository;

import java.util.List;

public class RateServiceImpl implements RateService {

    private ReviewsRepository reviewsRepository;
    private AccountsRepository accountsRepository;

    public RateServiceImpl(ReviewsRepository reviewsRepository, AccountsRepository accountsRepository){
        this.reviewsRepository = reviewsRepository;
        this.accountsRepository = accountsRepository;
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
        else {
            for (Review review: reviews){
                Account account = accountsRepository.findById(review.getAccount().getId()).get();
                review.setAccount(account);
            }
            return reviews;
        }
    }
}
