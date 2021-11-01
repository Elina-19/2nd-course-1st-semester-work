package ru.itis.zagidullina.readl.repositories;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.itis.zagidullina.readl.models.Book;
import ru.itis.zagidullina.readl.models.Chapter;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class ChapterRepositoryImpl implements ChapterRepository {

    //language=SQL
    private static final String SQL_GET_CHAPTERS_OF_BOOK = "select id, name, book_id, content_path, mime_type from chapter " +
            "where chapter.book_id = :bookId";

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private final RowMapper<Chapter> chapterRowMapper = (row, rowNumber) -> Chapter.builder()
            .id(row.getInt("id"))
            .name(row.getString("name"))
            .mimeType(row.getString("mime_type"))
            .contentPath(row.getString("content_path"))
            .build();

    public ChapterRepositoryImpl(DataSource dataSource){
        jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public List<Chapter> getChaptersOfBook(Integer id) {
        return jdbcTemplate.query(SQL_GET_CHAPTERS_OF_BOOK, Collections.singletonMap("bookId", id),chapterRowMapper);
    }

    @Override
    public Optional<Chapter> findById(Integer id) {
        return Optional.empty();
    }

    @Override
    public void save(Chapter chapter) {

    }
}
