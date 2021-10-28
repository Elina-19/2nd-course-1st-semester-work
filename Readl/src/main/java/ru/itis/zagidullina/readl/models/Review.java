package ru.itis.zagidullina.readl.models;

import lombok.Builder;
import lombok.Data;

import java.sql.Date;

@Data
@Builder
public class Review {

    private Integer id;
    private Date date;
    private Account account;
    private Book book;
    private Double rate;
    private String contentPath;
}
