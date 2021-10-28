package ru.itis.zagidullina.readl.models;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Chapter {
    private Integer id;
    private String name;
    private Book book;
    private String contentPath;

    private List<Comment> comments;
}
