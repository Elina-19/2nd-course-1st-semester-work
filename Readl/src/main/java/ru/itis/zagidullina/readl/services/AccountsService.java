package ru.itis.zagidullina.readl.services;

import ru.itis.zagidullina.readl.models.Account;
import ru.itis.zagidullina.readl.models.Favourite;

import java.util.List;

public interface AccountsService {
    Account findByEmail(String email);
    void addToFavourite(Account account, Integer bookId);
    void deleteFromFavourite(Account account, Integer bookId);
    Favourite getFavourite(Account account);
    boolean getStatus(Account account, Integer bookId);
}
