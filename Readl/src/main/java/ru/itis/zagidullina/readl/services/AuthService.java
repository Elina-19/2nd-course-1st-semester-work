package ru.itis.zagidullina.readl.services;

import ru.itis.zagidullina.readl.dto.SignInForm;
import ru.itis.zagidullina.readl.dto.SignUpForm;
import ru.itis.zagidullina.readl.models.Account;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface AuthService {
    Account signIn(SignInForm signInForm, HttpServletRequest request, HttpServletResponse response);
    void signUp(SignUpForm signUpForm);
    void logout(HttpServletRequest request, HttpServletResponse response);
    void updateUuid(String email, String uuid);
    boolean authenticateByToken(HttpServletRequest request);
    boolean authenticateByUUID(HttpServletRequest request);
}
