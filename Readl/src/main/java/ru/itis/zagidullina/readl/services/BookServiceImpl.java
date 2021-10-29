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

@Service
public class BookServiceImpl implements BookService {

    private BookRepository bookRepository;

    private static final Pattern patternEmpty = Pattern.compile("\\s+");
    private String storagePath;

    public BookServiceImpl(BookRepository bookRepository){
        this.bookRepository = bookRepository;
        storagePath = "C:\\files\\";
    }

    @Override
    public void save(AddBookForm addBookForm, Account account, InputStream fileInputStream) {
        if(addBookForm.getName() == null || addBookForm.getName().equals("") || patternEmpty.matcher(addBookForm.getName()).find()){
            throw new EmptyFieldException("Введите название книги");
        }

        if(addBookForm.getDescription() == null || addBookForm.getDescription().equals("") || patternEmpty.matcher(addBookForm.getDescription()).find()){
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
                .build();

        bookRepository.save(book);
    }

    @Override
    public Optional<Book> findById() {
        return Optional.empty();
    }

    @Override
    public List<Book> findBooksOfAccount(Integer id) {
        return null;
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
}
