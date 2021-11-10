package ru.itis.zagidullina.readl.repositories;

import ru.itis.zagidullina.readl.models.Account;

import java.util.Optional;

public interface AccountsRepository {
    Optional<Account> findById(Integer id);
    void save(Account account);
    Optional<Account> findByEmail(String email);
    void updateUuid(String email, String uuid);
    void updateToken(String email, String token);
    Optional<Account> findByToken(String token);
    Optional<Account> findByUUID(String uuid);
}
