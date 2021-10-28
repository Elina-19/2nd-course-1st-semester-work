package ru.itis.zagidullina.readl.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Favourite {
    private Account account;
    private Book[] books;
}
