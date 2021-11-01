package ru.itis.zagidullina.readl.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.itis.zagidullina.readl.dto.AddBookForm;
import ru.itis.zagidullina.readl.exceptions.EmptyFieldException;
import ru.itis.zagidullina.readl.models.Account;
import ru.itis.zagidullina.readl.models.Book;
import ru.itis.zagidullina.readl.repositories.BookRepository;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

public class BookServiceImpl implements BookService {

    private BookRepository bookRepository;
    private Validator validator;

    private String storagePath;

    public BookServiceImpl(BookRepository bookRepository, Validator validator, String storagePath){
        this.bookRepository = bookRepository;
        this.validator = validator;
        this.storagePath = storagePath;
    }

    @Override
    public void save(AddBookForm addBookForm, Account account, InputStream fileInputStream) {
        if(validator.isNull(addBookForm.getName()) || validator.isEmpty(addBookForm.getName())){
            throw new EmptyFieldException("Введите название книги");
        }

        if(validator.isNull(addBookForm.getDescription()) || validator.isEmpty(addBookForm.getDescription())){
            throw new EmptyFieldException("Введите описание");
        }

        String pathToBook = getUniqName(addBookForm.getName());
        new File(Paths.get(storagePath, pathToBook).toString()).mkdir();

        String imagePath = null;

        if(addBookForm.getSize() != 0){
            imagePath = saveImage(addBookForm.getImageName(), pathToBook, fileInputStream);
        }

        Book book = Book.builder()
                .account(account)
                .name(addBookForm.getName())
                .description(addBookForm.getDescription())
                .imagePath(imagePath)
                .numberOfComments(0)
                .numberOfRates(0)
                .numberOfReviews(0)
                .pathToDirectoryWithContent(pathToBook)
                .rate(0.0)
                .build();

        bookRepository.save(book);
    }

    @Override
    public Book findById(Integer id) {
        Optional<Book> optionalBook = bookRepository.findById(id);

        if (optionalBook.isPresent()){
            Book book = optionalBook.get();
            return book;
        }else throw new IllegalArgumentException("Такой книги нет");
    }

    @Override
    public List<Book> findBooksOfAccount(Integer id) {
        return bookRepository.findBooksOfAccount(id);
    }

    @Override
    public String saveImage(String fileName, String directoryPath, InputStream fileInputStream) {
        String name = getUniqName(fileName);

        try{
            Files.copy(fileInputStream, Paths.get(storagePath, directoryPath, name));
        }catch (IOException e){
            throw new IllegalArgumentException(e);
        }

        return name;
    }

    private String getUniqName(String name){
        String result = Math.round(Math.random()*1000) + 7*Math.round(Math.random()*1000) + name;
        return result;
    }

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }
}
