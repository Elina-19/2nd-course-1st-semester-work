package ru.itis.zagidullina.readl.repositories;

import ru.itis.zagidullina.readl.models.Account;
import ru.itis.zagidullina.readl.models.Book;
import ru.itis.zagidullina.readl.models.Favourite;

import java.util.List;

public interface FavouriteRepository {
    void addToFavourite(Account account, Integer bookId);
    void deleteFromFavourite(Account account, Integer bookId);
    Favourite getFavourite(Account account);
    String getStatus(Account account, Integer bookId);
}
