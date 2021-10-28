package ru.itis.zagidullina.readl.services;

import ru.itis.zagidullina.readl.dto.SignInForm;
import ru.itis.zagidullina.readl.dto.SignUpForm;
import ru.itis.zagidullina.readl.models.Account;

import javax.servlet.http.HttpSession;

public interface AuthService {
    Account signIn(SignInForm signInForm);
    void signUp(SignUpForm signUpForm);
    void logout(HttpSession session);
    void updateUuid(String email, String uuid);
}
