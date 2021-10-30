package ru.itis.zagidullina.readl.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
