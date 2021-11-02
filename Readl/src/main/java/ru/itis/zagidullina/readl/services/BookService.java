package ru.itis.zagidullina.readl.services;

import ru.itis.zagidullina.readl.dto.AddBookForm;
import ru.itis.zagidullina.readl.models.Account;
import ru.itis.zagidullina.readl.models.Book;
import ru.itis.zagidullina.readl.models.Chapter;
import ru.itis.zagidullina.readl.models.Genre;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;

public interface BookService {
    void saveBook(AddBookForm addBookForm, Account account, InputStream inputStream);
    void saveChapter(Chapter chapter, InputStream inputStream);
    Book findById(Integer id);
    List<Book> findBooksOfAccount(Integer id);
    //String uploadFile(String fileName, String directoryPath, InputStream inputStream);
    List<Book> findAll();
    List<Genre> getAllGenres();
}
