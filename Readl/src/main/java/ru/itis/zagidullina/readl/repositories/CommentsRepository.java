package ru.itis.zagidullina.readl.repositories;

import ru.itis.zagidullina.readl.models.Comment;

import java.util.List;

public interface CommentsRepository {
    void save(Comment comment);
    List<Comment> getCommentsOfBook(Integer id);
    List<Comment> getCommentsOfChapter(Integer id);
    List<Comment> getCommentsOfComment(Integer id);
}
