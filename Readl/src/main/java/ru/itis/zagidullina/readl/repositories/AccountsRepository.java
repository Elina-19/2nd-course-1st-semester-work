package ru.itis.zagidullina.readl.repositories;

import ru.itis.zagidullina.readl.models.Account;

import java.util.List;
import java.util.Optional;

public interface AccountsRepository {
    void save(Account account);
    Optional<Account> findByEmail(String email);
    List<Account> findAll();
    void updateUuid(String email, String uuid);
}
