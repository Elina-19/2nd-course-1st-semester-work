package ru.itis.zagidullina.readl.repositories;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.itis.zagidullina.readl.models.Book;
import ru.itis.zagidullina.readl.models.Chapter;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Repository
public class ChapterRepositoryImpl implements ChapterRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public ChapterRepositoryImpl(DataSource dataSource){
        jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public List<Chapter> getChaptersOfBook(Integer id) {
        return null;
    }

    @Override
    public Optional<Chapter> findById(Integer id) {
        return Optional.empty();
    }

    @Override
    public void save(Chapter chapter) {

    }
}
