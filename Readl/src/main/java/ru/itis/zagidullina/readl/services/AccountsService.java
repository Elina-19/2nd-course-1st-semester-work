package ru.itis.zagidullina.readl.services;

import ru.itis.zagidullina.readl.models.Account;

import java.util.List;

public interface AccountsService {
    Account findByEmail(String email);
    List<Account> findAll();
}
