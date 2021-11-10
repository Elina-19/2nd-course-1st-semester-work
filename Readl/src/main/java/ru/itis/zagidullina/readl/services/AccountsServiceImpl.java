package ru.itis.zagidullina.readl.services;

import ru.itis.zagidullina.readl.exceptions.InvalidEmailException;
import ru.itis.zagidullina.readl.models.Account;
import ru.itis.zagidullina.readl.models.Favourite;
import ru.itis.zagidullina.readl.repositories.AccountsRepository;
import ru.itis.zagidullina.readl.repositories.FavouriteRepository;

import java.util.Optional;

public class AccountsServiceImpl implements AccountsService {

    private final AccountsRepository accountsRepository;
    private final FavouriteRepository favouriteRepository;

    private static final String STATUS_ACTIVE = "active";
    private static final String STATUS_INACTIVE = "inactive";

    public AccountsServiceImpl(AccountsRepository accountsRepository, FavouriteRepository favouriteRepository){
        this.accountsRepository = accountsRepository;
        this.favouriteRepository = favouriteRepository;
    }

    @Override
    public Account findByEmail(String email) {
        Optional<Account> accountOptional = accountsRepository.findByEmail(email);

        if (!accountOptional.isPresent()){
            throw new InvalidEmailException("Профиля с таким email не существует");
        }
        else{
            return accountOptional.get();
        }
    }

    @Override
    public void addToFavourite(Account account, Integer bookId) {
        favouriteRepository.addToFavourite(account, bookId);
    }

    @Override
    public void deleteFromFavourite(Account account, Integer bookId) {
        favouriteRepository.deleteFromFavourite(account, bookId);
    }

    @Override
    public Favourite getFavourite(Account account) {
        return favouriteRepository.getFavourite(account);
    }

    @Override
    public boolean getStatus(Account account, Integer bookId) {
        String status = favouriteRepository.getStatus(account, bookId);

        if (status == null || status.equals(STATUS_INACTIVE)){
            return false;
        }
        else return true;
    }
}
