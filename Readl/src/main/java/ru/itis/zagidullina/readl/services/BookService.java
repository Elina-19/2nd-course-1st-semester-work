package ru.itis.zagidullina.readl.services;

import ru.itis.zagidullina.readl.dto.AddBookForm;
import ru.itis.zagidullina.readl.models.Account;
import ru.itis.zagidullina.readl.models.Book;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;

public interface BookService {
    void save(AddBookForm addBookForm, Account account, InputStream inputStream);
    Optional<Book> findById();
    List<Book> findBooksOfAccount(Integer id);
    String saveImage(String fileName, String directoryPath, InputStream inputStream);
}
