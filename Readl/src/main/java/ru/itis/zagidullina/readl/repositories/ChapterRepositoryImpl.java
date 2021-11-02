package ru.itis.zagidullina.readl.repositories;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.itis.zagidullina.readl.models.Book;
import ru.itis.zagidullina.readl.models.Chapter;

import javax.sql.DataSource;
import java.util.*;

public class ChapterRepositoryImpl implements ChapterRepository {

    //language=SQL
    private static final String SQL_GET_CHAPTERS_OF_BOOK = "select id, name, book_id, content_path, mime_type from chapter " +
            "where chapter.book_id = :bookId";

    //language=SQL
    private static final String SQL_SAVE_CHAPTER = "insert into chapter(name, book_id, content_path, mime_type) values " +
            "(:name, :bookId, :contentPath, :mimeType)";

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
        KeyHolder keyHolder = new GeneratedKeyHolder();
        Map<String, Object> map = new HashMap<>();
        map.put("name", chapter.getName());
        map.put("bookId", chapter.getBook().getId());
        map.put("contentPath", chapter.getContentPath());
        map.put("mimeType", chapter.getMimeType());

        SqlParameterSource parameterSource = new MapSqlParameterSource(map);

        jdbcTemplate.update(SQL_SAVE_CHAPTER, parameterSource, keyHolder, new String[]{"id"});
        chapter.setId(keyHolder.getKeyAs(Integer.class));
    }
}
