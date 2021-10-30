package ru.itis.zagidullina.readl.repositories;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.itis.zagidullina.readl.models.Account;
import ru.itis.zagidullina.readl.models.Book;
import ru.itis.zagidullina.readl.repositories.BookRepository;

import javax.sql.DataSource;
import java.util.*;

@Repository
public class BookRepositoryJdbcTemplateImpl implements BookRepository {

    //language=SQL
    private static final String SQL_SAVE = "insert into book(name, account_id, path_to_content, image_path, date_add, description, rate, number_of_rates, number_of_comments, number_of_reviews) " +
            "values (:name, :accountId, :pathToContent, :imagePath, now(), :description, :rate, :numberOfRates, :numberOfComments, :numberOfReviews)";

    //language=SQL
    private static final String SQL_FIND_BY_ID = "select id, name, account_id, path_to_content, image_path, date_add, description, rate, number_of_rates, number_of_comments, number_of_reviews from book " +
            "where book.id = :id";

    //language=SQL
    private static final String SQL_FIND_BOOKS_OF_ACCOUNT_BY_ID = "select id, name, account_id, path_to_content, image_path, date_add, description, rate, number_of_rates, number_of_comments, number_of_reviews from book " +
            "where book.account_id = :id";

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public BookRepositoryJdbcTemplateImpl(DataSource dataSource){
        jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    private final RowMapper<Book> bookRowMapper = (row, rowNumber) -> Book.builder()
            .id(row.getInt("id"))
            .name(row.getString("name"))
            .account(Account.builder().id(row.getInt("account_id")).build())
            .pathToDirectoryWithContent("path_to_content")
            .imagePath(row.getString("image_path"))
            .dateOfAdding(row.getTimestamp("date_add"))
            .description(row.getString("description"))
            .rate(row.getDouble("rate"))
            .numberOfComments(row.getInt("number_of_comments"))
            .numberOfRates(row.getInt("number_of_rates"))
            .numberOfReviews(row.getInt("number_of_reviews"))
            .build();

    @Override
    public void save(Book book) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        Map<String, Object> values = new HashMap<>();
        values.put("name", book.getName());
        values.put("accountId", book.getAccount().getId());
        values.put("pathToContent", book.getPathToDirectoryWithContent());
        values.put("imagePath", book.getImagePath());
        values.put("description", book.getDescription());
        values.put("rate", book.getRate());
        values.put("numberOfRates", book.getNumberOfRates());
        values.put("numberOfComments", book.getNumberOfComments());
        values.put("numberOfReviews", book.getNumberOfReviews());

        SqlParameterSource sqlParameterSource = new MapSqlParameterSource(values);

        jdbcTemplate.update(SQL_SAVE, sqlParameterSource, keyHolder, new String[]{"id"});

        book.setId(keyHolder.getKeyAs(Integer.class));
        book.setChapters(new ArrayList<>());
        book.setComments(new ArrayList<>());
    }

    @Override
    public Optional<Book> findById(Integer id) {
        try {
            return Optional.of(jdbcTemplate.queryForObject(SQL_FIND_BY_ID, Collections.singletonMap("id", id), bookRowMapper));
        }catch (EmptyResultDataAccessException e){
            return Optional.empty();
        }
    }

    @Override
    public List<Book> findBooksOfAccount(Integer id) {
        return jdbcTemplate.query(SQL_FIND_BOOKS_OF_ACCOUNT_BY_ID, Collections.singletonMap("id", id), bookRowMapper);
    }
}
