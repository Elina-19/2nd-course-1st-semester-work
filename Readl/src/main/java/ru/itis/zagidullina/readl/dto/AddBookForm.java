package ru.itis.zagidullina.readl.dto;

import lombok.Builder;
import lombok.Data;
import ru.itis.zagidullina.readl.models.Genre;

import java.util.List;

@Data
@Builder
public class AddBookForm {
    private String name;
    private String description;
    private String imageName;
    private Long size;
    private String[] genres;
}
