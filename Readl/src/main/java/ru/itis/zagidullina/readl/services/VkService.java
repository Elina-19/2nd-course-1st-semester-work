package ru.itis.zagidullina.readl.services;

import com.google.gson.JsonObject;
import ru.itis.zagidullina.readl.models.Account;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface VkService {
    String getAuthorizationPath();
    Account signIn(String code, HttpServletRequest request, HttpServletResponse response);
}
