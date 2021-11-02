package ru.itis.zagidullina.readl.repositories;

import ru.itis.zagidullina.readl.models.Book;
import ru.itis.zagidullina.readl.models.Chapter;

import java.util.List;
import java.util.Optional;

public interface BookRepository {
    void saveBook(Book book);
    Optional<Book> findById(Integer id);
    List<Book> findBooksOfAccount(Integer id);
    List<Book> findAll();
    String getPathOfBookDirectory(Integer id);
    void saveChapter(Chapter chapter);
}
