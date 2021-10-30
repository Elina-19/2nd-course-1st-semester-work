package ru.itis.zagidullina.readl.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itis.zagidullina.readl.models.Account;
import ru.itis.zagidullina.readl.repositories.AccountsRepository;

import java.util.List;
import java.util.Optional;

public class AccountsServiceImpl implements AccountsService {

    private final AccountsRepository accountsRepository;

    public AccountsServiceImpl(AccountsRepository accountsRepository){
        this.accountsRepository = accountsRepository;
    }

    @Override
    public Account findByEmail(String email) {
        Optional<Account> accountOptional = accountsRepository.findByEmail(email);

        if (!accountOptional.isPresent()){
            throw new IllegalArgumentException("Такого пользователя нет");
        }
        else{
            return accountOptional.get();
        }
    }

    @Override
    public List<Account> findAll(){
        return accountsRepository.findAll();
    }
}
