package ru.itis.zagidullina.readl.repositories;

import com.sun.rowset.internal.Row;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import ru.itis.zagidullina.readl.models.Account;
import ru.itis.zagidullina.readl.models.Book;
import ru.itis.zagidullina.readl.models.Favourite;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FavouriteRepositoryImpl implements FavouriteRepository {

    private static final String STATUS_ACTIVE = "active";
    private static final String STATUS_INACTIVE = "inactive";

    //language=SQL
    private static final String SQL_ADD_TO_FAVOURITE = "insert into favourite(account_id, book_id, status) values (:accountId, :bookId, 'active')";

    //language=SQL
    private static final String SQL_UPDATE_STATUS = "update favourite f set status = :status where f.account_id = :accountId and f.book_id = :bookId";

    //language=SQL
    private static final String SQL_SELECT_FAVOURITE_BY_ACCOUNT_ID = "select account_id, book_id from favourite f where f.account_id = :accountId and f.status = 'active'";

    //language=SQL
    private static final String SQL_SELECT_STATUS = "select status from favourite f where f.account_id = :accountId and f.book_id = :bookId";

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private BookRepository bookRepository;

    private final RowMapper<String> statusRowMapper = (row, rowNumber) -> row.getString("status");

    private final RowMapper<Integer> favouriteRowMapper = (row, rowNumber) -> row.getInt("book_id");

    public FavouriteRepositoryImpl(DataSource dataSource, BookRepository bookRepository){
        jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.bookRepository = bookRepository;
    }

    @Override
    public void addToFavourite(Account account, Integer bookId) {
        Map<String, Object> map = new HashMap<>();
        map.put("accountId", account.getId());
        map.put("bookId", bookId);

        String status = getStatus(account, bookId);

        if (status != null){
            map.put("status", STATUS_ACTIVE);
            jdbcTemplate.update(SQL_UPDATE_STATUS, map);
        }else {
            jdbcTemplate.update(SQL_ADD_TO_FAVOURITE, map);
        }
    }

    @Override
    public String getStatus(Account account, Integer bookId) {
        Map<String, Object> map = new HashMap<>();
        map.put("accountId", account.getId());
        map.put("bookId", bookId);

        try {
            return jdbcTemplate.queryForObject(SQL_SELECT_STATUS, map, statusRowMapper);
        }catch (EmptyResultDataAccessException e){
            return null;
        }
    }

    @Override
    public void deleteFromFavourite(Account account, Integer bookId) {
        Map<String, Object> map = new HashMap<>();
        map.put("accountId", account.getId());
        map.put("bookId", bookId);
        map.put("status", STATUS_INACTIVE);

        jdbcTemplate.update(SQL_UPDATE_STATUS, map);
    }

    @Override
    public Favourite getFavourite(Account account) {
        List<Book> books = new ArrayList<>();

        Map<String, Object> map = new HashMap<>();
        map.put("accountId", account.getId());

        List<Integer> bookIds;
        try {
            bookIds = jdbcTemplate.query(SQL_SELECT_FAVOURITE_BY_ACCOUNT_ID, map, favouriteRowMapper);
        }catch (EmptyResultDataAccessException e){
            return null;
        }

        for (Integer id: bookIds){
            Book book = bookRepository.findById(id).get();
            books.add(book);
        }

        Favourite favourite = Favourite.builder()
                .account(account)
                .books(books)
                .build();

        return favourite;
    }
}
