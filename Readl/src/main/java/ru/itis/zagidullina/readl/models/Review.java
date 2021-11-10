package ru.itis.zagidullina.readl.models;

import lombok.Builder;
import lombok.Data;

import java.sql.Date;
import java.sql.Timestamp;

@Data
@Builder
public class Review {

    private Integer id;
    private Timestamp date;
    private Account account;
    private Book book;
    private String content;
}
