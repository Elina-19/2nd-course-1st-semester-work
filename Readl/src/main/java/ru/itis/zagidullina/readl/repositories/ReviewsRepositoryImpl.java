package ru.itis.zagidullina.readl.repositories;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import ru.itis.zagidullina.readl.models.Account;
import ru.itis.zagidullina.readl.models.Review;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReviewsRepositoryImpl implements ReviewsRepository {

    //language=SQL
    private static final String SQL_SAVE = "insert into review(date, account_id, book_id, content) " +
            "values (now(), :accountId, :bookId, :content)";

    //language=SQL
    private static final String SQL_GET_REVIEWS_OF_BOOK = "select id, date, account_id, book_id, content " +
            "from review where review.id > :count and review.book_id = :bookId";

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private final RowMapper<Review> reviewRowMapper = (row, rowNumber) -> Review.builder()
            .id(row.getInt("id"))
            .content(row.getString("content"))
            .account(Account.builder()
                    .id(row.getInt("account_id"))
                    .build())
            .build();

    public ReviewsRepositoryImpl(DataSource dataSource){
        jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public void save(Review review) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        Map<String, Object> values = new HashMap<>();
        values.put("accountId", review.getAccount().getId());
        values.put("bookId", review.getBook().getId());
        values.put("content", review.getContent());

        SqlParameterSource sqlParameterSource = new MapSqlParameterSource(values);

        jdbcTemplate.update(SQL_SAVE, sqlParameterSource, keyHolder, new String[]{"id"});

        review.setId(keyHolder.getKeyAs(Integer.class));
    }

    @Override
    public List<Review> getReviewsOfBook(Integer id, Integer count) {
        Map<String, Object> map = new HashMap<>();

        map.put("bookId", id);
        map.put("count", count);

        SqlParameterSource sqlParameterSource = new MapSqlParameterSource(map);

        return jdbcTemplate.query(SQL_GET_REVIEWS_OF_BOOK, sqlParameterSource, reviewRowMapper);
    }
}
