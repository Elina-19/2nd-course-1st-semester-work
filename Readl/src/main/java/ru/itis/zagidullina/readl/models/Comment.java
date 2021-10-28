package ru.itis.zagidullina.readl.models;

import lombok.Builder;
import lombok.Data;

import java.awt.print.Book;
import java.sql.Date;
import java.util.List;

@Data
@Builder
public class Comment {
    private Integer id;
    private Date date;
    private String type;
    private Account account;
    private String content;
    private Integer numberOfPage;

    private List<Comment> innerComments;
}
