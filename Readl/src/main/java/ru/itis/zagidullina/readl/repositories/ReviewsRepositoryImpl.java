package ru.itis.zagidullina.readl.repositories;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import ru.itis.zagidullina.readl.models.Review;

import javax.sql.DataSource;
import java.util.List;

public class ReviewsRepositoryImpl implements ReviewsRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public ReviewsRepositoryImpl(DataSource dataSource){
        jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public void save(Review review) {

    }

    @Override
    public List<Review> getReviewsOfBook(Integer id) {
        return null;
    }
}
