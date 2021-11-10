package ru.itis.zagidullina.readl.repositories;

import ru.itis.zagidullina.readl.models.Account;
import ru.itis.zagidullina.readl.models.Book;
import ru.itis.zagidullina.readl.models.Chapter;
import ru.itis.zagidullina.readl.models.Genre;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import javax.sql.DataSource;
import java.util.*;

public class BookRepositoryJdbcTemplateImpl implements BookRepository {

    //language=SQL
    private static final String SQL_SAVE = "insert into book(name, account_id, path_to_content, image_path, date_add, description, rate, number_of_rates, number_of_comments, number_of_reviews) " +
            "values (:name, :accountId, :pathToContent, :imagePath, now(), :description, :rate, :numberOfRates, :numberOfComments, :numberOfReviews)";

    //language=SQL
    private static final String SQL_FIND_BY_ID = "select id, name, account_id, path_to_content, image_path, date_add, description, rate, number_of_rates, number_of_comments, number_of_reviews from book " +
            "where book.id = :id";

    //language=SQL
    private static final String SQL_FIND_ALL = "select id, name, account_id, path_to_content, image_path, date_add, description, rate, number_of_rates, number_of_comments, number_of_reviews from book";

    //language=SQL
    private static final String SQL_SEARCH = "select id, name, account_id, path_to_content, image_path, date_add, description, rate, number_of_rates, number_of_comments, number_of_reviews from book where name ~* :regular";

    //language=SQL
    private static final String SQL_FIND_BOOKS_OF_ACCOUNT_BY_ID = "select id, name, account_id, path_to_content, image_path, date_add, description, rate, number_of_rates, number_of_comments, number_of_reviews from book " +
            "where book.account_id = :id";

    //language=SQL
    private static final String SQL_GET_PATH_OF_BOOK_DIRECTORY = "select path_to_content from book where book.id = :id";

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private AccountsRepository accountsRepository;
    private ChapterRepository chapterRepository;
    private GenreRepository genreRepository;
    private CommentsRepository commentsRepository;
    private ReviewsRepository reviewsRepository;

    public BookRepositoryJdbcTemplateImpl(DataSource dataSource, AccountsRepository accountsRepository, ChapterRepository chapterRepository, CommentsRepository commentsRepository, ReviewsRepository reviewsRepository, GenreRepository genreRepository){
        jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.accountsRepository = accountsRepository;
        this.chapterRepository = chapterRepository;
        this.genreRepository = genreRepository;
        this.commentsRepository = commentsRepository;
        this.reviewsRepository = reviewsRepository;
    }

    private final RowMapper<Book> bookRowMapper = (row, rowNumber) -> Book.builder()
            .id(row.getInt("id"))
            .name(row.getString("name"))
            .account(Account.builder().id(row.getInt("account_id")).build())
            .pathToDirectoryWithContent(row.getString("path_to_content"))
            .imagePath(row.getString("image_path"))
            .dateOfAdding(row.getTimestamp("date_add"))
            .description(row.getString("description"))
            .rate(row.getDouble("rate"))
            .numberOfComments(row.getInt("number_of_comments"))
            .numberOfRates(row.getInt("number_of_rates"))
            .numberOfReviews(row.getInt("number_of_reviews"))
            .build();

    private final RowMapper<Book> pathRowMapper = (row, rowNumber) -> Book.builder()
            .pathToDirectoryWithContent(row.getString("path_to_content"))
            .build();

    @Override
    public void saveBook(Book book) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        Map<String, Object> values = new HashMap<>();
        values.put("name", book.getName());
        values.put("accountId", book.getAccount().getId());
        values.put("pathToContent", book.getPathToDirectoryWithContent());
        values.put("imagePath", book.getImagePath());
        values.put("description", book.getDescription());
        values.put("rate", book.getRate());
        values.put("numberOfRates", book.getNumberOfRates());
        values.put("numberOfComments", book.getNumberOfComments());
        values.put("numberOfReviews", book.getNumberOfReviews());

        SqlParameterSource sqlParameterSource = new MapSqlParameterSource(values);

        jdbcTemplate.update(SQL_SAVE, sqlParameterSource, keyHolder, new String[]{"id"});

        book.setId(keyHolder.getKeyAs(Integer.class));
        genreRepository.saveGenresOfBook(book.getGenres(), book.getId());
    }

    @Override
    public Optional<Book> findById(Integer id) {
        try {
            Book book =  jdbcTemplate.queryForObject(SQL_FIND_BY_ID, Collections.singletonMap("id", id), bookRowMapper);

            Account account = accountsRepository.findById(book.getAccount().getId()).get();
            List<Chapter> chapters = chapterRepository.getChaptersOfBook(book.getId());
            List<Genre> genres = genreRepository.findGenresOfBook(book.getId());

            book.setAccount(account);
            book.setChapters(chapters);
            book.setGenres(genres);

            return Optional.of(book);
        }catch (EmptyResultDataAccessException e){
            return Optional.empty();
        }
    }

    @Override
    public List<Book> findBooksOfAccount(Integer id) {
        return jdbcTemplate.query(SQL_FIND_BOOKS_OF_ACCOUNT_BY_ID, Collections.singletonMap("id", id), bookRowMapper);
    }

    @Override
    public List<Book> findAll() {
        return jdbcTemplate.query(SQL_FIND_ALL, bookRowMapper);
    }

    @Override
    public String getPathOfBookDirectory(Integer id) {
        return jdbcTemplate.queryForObject(SQL_GET_PATH_OF_BOOK_DIRECTORY, Collections.singletonMap("id", id), pathRowMapper).getPathToDirectoryWithContent();
    }

    @Override
    public void saveChapter(Chapter chapter) {
        chapterRepository.save(chapter);
    }

    @Override
    public Optional<Chapter> getChapterById(Integer id) {
        Optional<Chapter> optionalChapter = chapterRepository.findById(id);

        if (optionalChapter.isPresent()){
            Chapter chapter = optionalChapter.get();
            String pathOfBookDirectory = getPathOfBookDirectory(chapter.getBook().getId());

            chapter.getBook().setPathToDirectoryWithContent(pathOfBookDirectory);
            return Optional.of(chapter);
        }else return Optional.empty();
    }

    @Override
    public List<Book> search(String str) {
        String regular = ".*" + str + ".*";

        return jdbcTemplate.query(SQL_SEARCH, Collections.singletonMap("regular", regular), bookRowMapper);
    }
}
