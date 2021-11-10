package ru.itis.zagidullina.readl.services;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import ru.itis.zagidullina.readl.models.Account;
import ru.itis.zagidullina.readl.repositories.AccountsRepository;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.UUID;

public class VkServiceImpl implements VkService {

    private static final String APP_ID = "7996071";
    private static final String PROTECTED_KEY = "OAkByqCG3jZYCT36Zl3H";

    private static final String REDIRECT_URI = "http://localhost:8080/Readl/signInVk";
    private static final String API_VERSION = "5.131";

    private static final String AUTHORIZATION_PATH = "https://oauth.vk.com/authorize?client_id=" + APP_ID + "&display=page&redirect_uri=" + REDIRECT_URI + "&scope=email&response_type=code&v=" + API_VERSION;
    private static final String GET_TOKEN = "https://oauth.vk.com/access_token?client_id=" + APP_ID + "&client_secret=" + PROTECTED_KEY + "&redirect_uri=" + REDIRECT_URI + "&code=";

    private static final String TOKEN_COOKIE = "token";
    private static final int AGE_OF_COOKIE = 60*60*24*180;

    private AccountsRepository accountsRepository;
    private Gson gson;

    public VkServiceImpl(AccountsRepository accountsRepository){
        this.accountsRepository = accountsRepository;
        gson = new Gson();
    }

    @Override
    public String getAuthorizationPath() {
        return AUTHORIZATION_PATH;
    }

    @Override
    public Account signIn(String code, HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();

        JsonObject tokenAndId = getJsonToken(code);

        String accessToken = tokenAndId.get("access_token").getAsString();
        String id = tokenAndId.get("user_id").getAsString();
        String email = tokenAndId.get("email").getAsString();

        Optional<Account> optionalAccount = accountsRepository.findByEmail(email);
        Account account;

        if (optionalAccount.isPresent()){
            account = optionalAccount.get();

        }else {
            account = getDataByToken(accessToken, id);
            account.setEmail(email);
            account.setToken(getToken());

            accountsRepository.save(account);
        }

        account.setUuid(session.getId());
        accountsRepository.updateUuid(account.getEmail(), session.getId());

        session.setAttribute("account", account);

        Cookie cookie = new Cookie(TOKEN_COOKIE, account.getToken());
        cookie.setMaxAge(AGE_OF_COOKIE);
        response.addCookie(cookie);

        return account;
    }

    private JsonObject getJsonToken(String code) {
        try {
            URLConnection connection = new URL(GET_TOKEN + code).openConnection();
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
            JsonObject data = gson.fromJson(br, JsonObject.class);

            return data;
        }catch (IOException e){
            throw new IllegalArgumentException("Problems with connection", e);
        }
    }

    private Account getDataByToken(String accessToken, String id){
        try {
            URLConnection connection = new URL("https://api.vk.com/method/users.get?user_ids=" + id + "&access_token=" + accessToken + "&v=" + API_VERSION).openConnection();
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));

            JsonObject data = gson.fromJson(br, JsonObject.class);
            Account account = getAccountFromJson(data);

            return account;
        }catch (IOException e){
            throw new IllegalArgumentException("Problems with connection", e);
        }
    }

    private Account getAccountFromJson(JsonObject jsonObject){
        JsonObject jsonAccount = jsonObject.getAsJsonArray("response").get(0).getAsJsonObject();
        Account account = Account.builder()
                .nickname(jsonAccount.get("first_name").getAsString())
                .build();

        return account;
    }

    private String getToken(){
        return UUID.randomUUID().toString();
    }
}
