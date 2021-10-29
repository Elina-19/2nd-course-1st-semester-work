package ru.itis.zagidullina.readl.repositories;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.itis.zagidullina.readl.models.Book;
import ru.itis.zagidullina.readl.repositories.BookRepository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Repository
public class BookRepositoryJdbcTemplateImpl implements BookRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public BookRepositoryJdbcTemplateImpl(DataSource dataSource){
        jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public void save(Book book) {

    }

    @Override
    public Optional<Book> findById() {
        return Optional.empty();
    }

    @Override
    public List<Book> findBooksOfAccount(Integer id) {
        return null;
    }
}
