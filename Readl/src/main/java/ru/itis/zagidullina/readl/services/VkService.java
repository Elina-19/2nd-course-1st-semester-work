package ru.itis.zagidullina.readl.services;

import com.google.gson.JsonObject;
import ru.itis.zagidullina.readl.models.Account;

public interface VkService {
    String getAuthorizationPath();
    Account signIn(String code);
}
