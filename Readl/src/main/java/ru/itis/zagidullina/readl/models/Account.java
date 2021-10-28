package ru.itis.zagidullina.readl.models;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Builder
public class Account {
    private Integer id;
    private String nickname;
    private String email;
    private String password;
    private String imagePath;
    private String uuid;

    private List<Account> followers;
    private List<Account> following;
}
