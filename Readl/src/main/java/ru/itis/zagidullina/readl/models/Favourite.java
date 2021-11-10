package ru.itis.zagidullina.readl.models;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
public class Favourite {
    private Account account;
    private List<Book> books;
}
