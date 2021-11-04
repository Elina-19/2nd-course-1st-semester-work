package ru.itis.zagidullina.readl.services;

import ru.itis.zagidullina.readl.dto.AddBookForm;
import ru.itis.zagidullina.readl.exceptions.EmptyFieldException;
import ru.itis.zagidullina.readl.models.Account;
import ru.itis.zagidullina.readl.models.Book;
import ru.itis.zagidullina.readl.models.Chapter;
import ru.itis.zagidullina.readl.models.Genre;
import ru.itis.zagidullina.readl.repositories.BookRepository;
import ru.itis.zagidullina.readl.repositories.GenreRepository;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BookServiceImpl implements BookService {

    private BookRepository bookRepository;
    private GenreRepository genreRepository;
    private Validator validator;

    private String storagePath;

    public BookServiceImpl(BookRepository bookRepository, GenreRepository genreRepository, Validator validator, String storagePath){
        this.bookRepository = bookRepository;
        this.genreRepository = genreRepository;
        this.validator = validator;
        this.storagePath = storagePath;
    }

    @Override
    public void saveBook(AddBookForm addBookForm, Account account, InputStream fileInputStream) {
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
            imagePath = uploadFile(addBookForm.getImageName(), pathToBook, fileInputStream);
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
                .genres(getGenresFromId(addBookForm.getGenres()))
                .build();

        bookRepository.saveBook(book);
    }

    @Override
    public void saveChapter(Chapter chapter, InputStream fileInputStream) {
        if(validator.isNull(chapter.getName()) || validator.isEmpty(chapter.getName())){
            throw new EmptyFieldException("Введите название главы");
        }

        if(chapter.getSize() == 0){
            throw new NullPointerException("Загрузите файл с главой");
        }

        String bookDirectoryPath = bookRepository.getPathOfBookDirectory(chapter.getBook().getId());
        String chapterPath = uploadFile(chapter.getName(), bookDirectoryPath, fileInputStream);
        chapter.setContentPath(chapterPath);

        bookRepository.saveChapter(chapter);
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

    public String uploadFile(String fileName, String directoryPath, InputStream fileInputStream) {
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

    @Override
    public List<Genre> getAllGenres() {
        return genreRepository.findAll();
    }

    private List<Genre> getGenresFromId(String[] ids) {
        try {
            List<Genre> genres = new ArrayList<>();

            for (String id : ids) {
                Genre genre = Genre.builder()
                        .id(Integer.valueOf(id))
                        .build();
                genres.add(genre);
            }

            return genres;
        }catch (NullPointerException e) {
            return null;
        }
    }

    @Override
    public Chapter getChapterById(Integer id) {
        Optional<Chapter> optionalChapter = bookRepository.getChapterById(id);

        if(optionalChapter.isPresent()){
            Chapter chapter = optionalChapter.get();
            chapter.setContent(getChapterContent(chapter));

            return chapter;
        }else throw new IllegalArgumentException("Такой главы нет");
    }

    private String getChapterContent(Chapter chapter){
        Path path = Paths.get(storagePath, chapter.getBook().getPathToDirectoryWithContent(), chapter.getContentPath());
        System.out.println(path.toString());
        try(BufferedReader br = new BufferedReader(
                        new InputStreamReader(
                        new FileInputStream(path.toString()), StandardCharsets.UTF_8))){
            StringBuilder sb = new StringBuilder();
            int current;

            while ((current = br.read()) != -1){
                char a = (char)current;
                sb.append(a);
            }

            return sb.toString();
        }catch (IOException e){
            throw new IllegalArgumentException("This file doesn't exist");
        }
    }

    @Override
    public OutputStream downloadImage(Book book, OutputStream fileOutputStream) {
        if (book.getImagePath() == null){
            return null;
        }

        try{
            Files.copy(Paths.get(storagePath, book.getPathToDirectoryWithContent(), book.getImagePath()), fileOutputStream);
            return fileOutputStream;
        }catch (IOException e){
            throw new IllegalArgumentException("File doesn't exist");
        }
    }
}
