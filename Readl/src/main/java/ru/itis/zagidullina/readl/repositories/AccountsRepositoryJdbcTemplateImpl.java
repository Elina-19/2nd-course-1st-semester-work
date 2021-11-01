package ru.itis.zagidullina.readl.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.itis.zagidullina.readl.models.Account;

import javax.sql.DataSource;
import java.util.*;

public class AccountsRepositoryJdbcTemplateImpl implements AccountsRepository{

    //language=SQl
    private static final String SQL_SAVE_ACCOUNT = "insert into account(nickname, email, password) " +
            "values(:nickname, :email, :password)";

    //language=SQL
    private static final String SQL_FIND_BY_EMAIl = "select id, uuid, nickname, email, password, image_path from account where account.email = :email";

    //language=SQL
    private static final String SQL_FIND_BY_ID = "select id, uuid, nickname, email, password, image_path from account where account.id = :id";

    //language=SQL
    private static final String SQL_SELECT_ALL = "select * from account order by id";

    //language=SQL
    private static final String SQL_UPDATE_UUID = "update account set uuid = :uuid where account.email = :email";

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public AccountsRepositoryJdbcTemplateImpl(DataSource dataSource){
        namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    private final RowMapper<Account> accountRowMapper = (row, rowNumber) -> Account.builder()
            .id(row.getInt("id"))
            .nickname(row.getString("nickname"))
            .email(row.getString("email"))
            .password(row.getString("password"))
            .build();

    @Override
    public void save(Account account) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        Map<String, Object> values = new HashMap<>();
        values.put("nickname", account.getNickname());
        values.put("email", account.getEmail());
        values.put("password", account.getPassword());

        SqlParameterSource sqlParameterSource = new MapSqlParameterSource(values);

        namedParameterJdbcTemplate.update(SQL_SAVE_ACCOUNT, sqlParameterSource, keyHolder, new String[]{"id"});

        account.setId(keyHolder.getKeyAs(Integer.class));
        account.setFollowers(new ArrayList<>());
        account.setFollowing(new ArrayList<>());
    }

    @Override
    public Optional<Account> findByEmail(String email) {
        try{
            return Optional.of(namedParameterJdbcTemplate.queryForObject(SQL_FIND_BY_EMAIl,
                    Collections.singletonMap("email", email), accountRowMapper));
        }
        catch (EmptyResultDataAccessException e){
            return Optional.empty();
        }
    }

    @Override
    public Optional<Account> findById(Integer id) {
        try{
            return Optional.of(namedParameterJdbcTemplate.queryForObject(SQL_FIND_BY_ID,
                    Collections.singletonMap("id", id), accountRowMapper));
        }
        catch (EmptyResultDataAccessException e){
            return Optional.empty();
        }
    }

    @Override
    public void updateUuid(String email, String uuid) {
        Map<String, Object> map = new HashMap<>();
        map.put("email", email);
        map.put("uuid", uuid);

        namedParameterJdbcTemplate.update(SQL_UPDATE_UUID, map);
    }

    @Override
    public List<Account> findAll(){
        return namedParameterJdbcTemplate.query(SQL_SELECT_ALL, accountRowMapper);
    }
}
