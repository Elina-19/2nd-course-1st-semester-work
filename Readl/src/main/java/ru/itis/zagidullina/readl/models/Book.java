package ru.itis.zagidullina.readl.models;

import lombok.*;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Timestamp;
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
    private Timestamp dateOfAdding;
    private String description;
    private Double rate;
    private Integer numberOfRates;
    private Integer numberOfComments;
    private Integer numberOfReviews;

    private List<Chapter> chapters;
    private List<Genre> genres;
    private List<Comment> comments;
    private List<Review> reviews;
}
