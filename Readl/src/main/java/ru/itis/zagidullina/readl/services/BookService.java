package ru.itis.zagidullina.readl.services;

import ru.itis.zagidullina.readl.dto.AddBookForm;
import ru.itis.zagidullina.readl.models.Account;
import ru.itis.zagidullina.readl.models.Book;
import ru.itis.zagidullina.readl.models.Chapter;
import ru.itis.zagidullina.readl.models.Genre;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

public interface BookService {
    void saveBook(AddBookForm addBookForm, Account account, InputStream inputStream);
    void saveChapter(Chapter chapter, InputStream inputStream);
    Book findById(Integer id);
    List<Book> findBooksOfAccount(Integer id);
    List<Book> findAll();
    List<Genre> getAllGenres();
    Chapter getChapterById(Integer id);
    OutputStream downloadImage(Book book, OutputStream fileOutputStream);
    List<Book> search(String str);
}
