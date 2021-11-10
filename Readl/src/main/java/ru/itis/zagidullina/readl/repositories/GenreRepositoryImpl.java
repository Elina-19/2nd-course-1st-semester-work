package ru.itis.zagidullina.readl.repositories;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import ru.itis.zagidullina.readl.models.Genre;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GenreRepositoryImpl implements GenreRepository {

    //language=SQL
    private static final String SQL_FIND_GENRES_OF_BOOK = "select g.id, g.name from genre g " +
            "inner join book_genre bg on g.id = bg.genre_id " +
            "inner join book b on bg.book_id = b.id where b.id = :id";

    //language=SQL
    private static final String SQL_FIND_ALL_GENRES = "select id, name from genre";

    //language=SQL
    private static final String SQL_SAVE_GENRES_OF_BOOK = "insert into book_genre(book_id, genre_id) values (:bookId, :genreId)";

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private final RowMapper<Genre> genreRowMapper = (row, rowNumber) -> Genre.builder()
            .id(row.getInt("id"))
            .name(row.getString("name"))
            .build();

    public GenreRepositoryImpl(DataSource dataSource){
        jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public List<Genre> findAll() {
        return jdbcTemplate.query(SQL_FIND_ALL_GENRES, genreRowMapper);
    }

    @Override
    public List<Genre> findGenresOfBook(Integer id) {
        return jdbcTemplate.query(SQL_FIND_GENRES_OF_BOOK, Collections.singletonMap("id", id),genreRowMapper);
    }

    @Override
    public void saveGenresOfBook(List<Genre> genres, Integer id) {
        if(genres != null){
            for (Genre genre: genres){
                Map<String, Object> map = new HashMap<>();
                map.put("bookId", id);
                map.put("genreId", genre.getId());

                jdbcTemplate.update(SQL_SAVE_GENRES_OF_BOOK, map);
            }
        }
    }
}
