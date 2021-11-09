package ru.itis.zagidullina.readl.services;

import ru.itis.zagidullina.readl.dto.SignInForm;
import ru.itis.zagidullina.readl.dto.SignUpForm;
import ru.itis.zagidullina.readl.exceptions.InvalidEmailException;
import ru.itis.zagidullina.readl.exceptions.InvalidPasswordException;
import ru.itis.zagidullina.readl.exceptions.NotAvailablePasswordException;
import ru.itis.zagidullina.readl.models.Account;
import ru.itis.zagidullina.readl.repositories.AccountsRepository;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AuthServiceImpl implements AuthService{

    private AccountsRepository accountsRepository;
    private VkService vkService;
    private Validator validator;

    private static final Pattern patternEmail = Pattern.compile("[A-Za-z0-9_+-]+@[A-Za-z0-9]+\\.[A-Za-z]{2,6}");
    private static final String TOKEN_COOKIE = "token";
    private static final String UUID_COOKIE = "uuid";
    private static final int AGE_OF_COOKIE = 60*60*24*180;

    public AuthServiceImpl(AccountsRepository accountsRepository, VkService vkService, Validator validator){
        this.accountsRepository = accountsRepository;
        this.vkService = vkService;
        this.validator = validator;
    }

    @Override
    public Account signIn(SignInForm signInForm, HttpServletRequest request, HttpServletResponse response) {

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

        HttpSession httpSession = request.getSession(true);

        account.setUuid(httpSession.getId());
        updateUuid(account.getEmail(), httpSession.getId());

        Cookie cookie = new Cookie(UUID_COOKIE, account.getUuid());
        cookie.setMaxAge(AGE_OF_COOKIE);
        response.addCookie(cookie);

        httpSession.setAttribute("account", account);

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
        password = password + password.substring(0, password.length()/2);
        
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(password.getBytes());
            byte[] digest = md.digest();
            String hashPassword = DatatypeConverter.printHexBinary(digest).toLowerCase();
            return hashPassword;
        }catch (NoSuchAlgorithmException e){
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public boolean authenticateByToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();

        for (Cookie cookie: cookies){
            if (cookie.getName().equals(TOKEN_COOKIE)){
                Optional<Account> optionalAccount = accountsRepository.findByToken(cookie.getValue());

                if (optionalAccount.isPresent()){
                    HttpSession session = request.getSession();
                    session.setAttribute("account", optionalAccount.get());

                    return true;
                }else return false;
            }
        }

        return false;
    }

    @Override
    public boolean authenticateByUUID(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();

        for (Cookie cookie: cookies){
            if (cookie.getName().equals(UUID_COOKIE)){
                Optional<Account> optionalAccount = accountsRepository.findByUUID(cookie.getValue());

                if (optionalAccount.isPresent()){
                    HttpSession session = request.getSession();
                    session.setAttribute("account", optionalAccount.get());

                    return true;
                }else return false;
            }
        }

        return false;
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();

        deleteUUID(request, response);
        deleteToken(request, response);
        request.setAttribute("authenticated", false);

        session.removeAttribute("account");
    }

    private void deleteToken(HttpServletRequest request, HttpServletResponse response){
        Cookie[] cookies = request.getCookies();

        for (Cookie cookie: cookies){
            if (cookie.getName().equals(TOKEN_COOKIE)){
                cookie.setValue(null);
                response.addCookie(cookie);
            }
        }
    }

    private void deleteUUID(HttpServletRequest request, HttpServletResponse response){
        Cookie[] cookies = request.getCookies();

        for (Cookie cookie: cookies){
            if (cookie.getName().equals(UUID_COOKIE)){
                cookie.setValue(null);
                response.addCookie(cookie);
            }
        }
    }
}
