package ru.itis.zagidullina.readl.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itis.zagidullina.readl.dto.SignInForm;
import ru.itis.zagidullina.readl.dto.SignUpForm;
import ru.itis.zagidullina.readl.exceptions.InvalidEmailException;
import ru.itis.zagidullina.readl.exceptions.InvalidPasswordException;
import ru.itis.zagidullina.readl.exceptions.NotAvailablePasswordException;
import ru.itis.zagidullina.readl.models.Account;
import ru.itis.zagidullina.readl.repositories.AccountsRepository;

import javax.servlet.http.HttpSession;
import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AuthServiceImpl implements AuthService{

    private AccountsRepository accountsRepository;
    private Validator validator;

    private static final Pattern patternEmail = Pattern.compile("[A-Za-z0-9_+-]+@[A-Za-z0-9]+\\.[A-Za-z]{2,6}");

    public AuthServiceImpl(AccountsRepository accountsRepository, Validator validator){
        this.accountsRepository = accountsRepository;
        this.validator = validator;
    }

    @Override
    public Account signIn(SignInForm signInForm) {

        if(validator.isNull(signInForm.getEmail()) || validator.isEmpty(signInForm.getEmail())){
            throw new NullPointerException("Введите email");
        }

        if(validator.isNull(signInForm.getPassword()) || validator.isEmpty(signInForm.getPassword())){
            throw new NullPointerException("Введите пароль");
        }

        Optional<Account> accountOptional = accountsRepository.findByEmail(signInForm.getEmail());

        if (!accountOptional.isPresent()){
            throw new InvalidEmailException("Профиля с таким email не существует");
        }

        Account account = accountOptional.get();

        if(!hashPassword(signInForm.getPassword()).equals(account.getPassword())){
            throw new InvalidPasswordException("Неверный пароль");
        }

        return account;
    }

    @Override
    public void updateUuid(String email, String uuid) {
        accountsRepository.updateUuid(email, uuid);
    }

    @Override
    public void signUp(SignUpForm signUpForm) {

        if(validator.isNull(signUpForm.getNickname()) || validator.isEmpty(signUpForm.getNickname())){
            throw new NullPointerException("Введите ник");
        }

        if(validator.isNull(signUpForm.getEmail()) || validator.isEmpty(signUpForm.getEmail())){
            throw new NullPointerException("Введите email");
        }

        if(validator.isNull(signUpForm.getPassword()) || validator.isEmpty(signUpForm.getPassword())){
            throw new NullPointerException("Введите пароль");
        }

        Matcher matcher = patternEmail.matcher(signUpForm.getEmail());
        if(!matcher.find()){
            throw new InvalidEmailException("Неправильный email");
        }

        if (accountsRepository.findByEmail(signUpForm.getEmail()).isPresent()){
            throw new NotAvailablePasswordException("Профиль с таким email уже существует");
        }

        Account account = Account.builder()
                .nickname(signUpForm.getNickname())
                .email(signUpForm.getEmail())
                .password(hashPassword(signUpForm.getPassword()))
                .build();

        accountsRepository.save(account);
    }

    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(password.getBytes());
            byte[] digest = md.digest();
            String hashPassword = DatatypeConverter.printHexBinary(digest).toLowerCase();
            return hashPassword;
        }catch (NoSuchAlgorithmException e){
            System.err.println(e);
        }
        return null;
    }

    @Override
    public void logout(HttpSession session) {
        session.removeAttribute("account");
        session.removeAttribute("isAuthenticated");
    }
}
