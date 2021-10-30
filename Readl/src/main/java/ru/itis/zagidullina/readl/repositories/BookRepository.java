package ru.itis.zagidullina.readl.repositories;

import ru.itis.zagidullina.readl.models.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository {
    void save(Book book);
    Optional<Book> findById(Integer id);
    List<Book> findBooksOfAccount(Integer id);
}
