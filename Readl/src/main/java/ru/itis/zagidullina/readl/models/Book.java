package ru.itis.zagidullina.readl.models;

import lombok.*;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Book {
    private Integer id;
    private String name;
    private Account account;
    private String pathToDirectoryWithContent;
    private String imagePath;
    private Date dateOfAdding;
    private String description;
    private Integer numberOfPages;
    private Double rate;
    private Integer numberOfRates;

    private List<Genre> genres;
    private List<Comment> comments;
}
