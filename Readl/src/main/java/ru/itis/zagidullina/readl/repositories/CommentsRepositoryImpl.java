package ru.itis.zagidullina.readl.repositories;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import ru.itis.zagidullina.readl.models.Comment;

import javax.sql.DataSource;
import java.util.List;

public class CommentsRepositoryImpl implements CommentsRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public CommentsRepositoryImpl(DataSource dataSource){
        jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public void save(Comment comment) {

    }

    @Override
    public List<Comment> getCommentsOfBook(Integer id) {
        return null;
    }

    @Override
    public List<Comment> getCommentsOfChapter(Integer id) {
        return null;
    }

    @Override
    public List<Comment> getCommentsOfComment(Integer id) {
        return null;
    }
}
