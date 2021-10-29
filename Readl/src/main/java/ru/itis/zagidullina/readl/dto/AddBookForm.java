package ru.itis.zagidullina.readl.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddBookForm {
    private String name;
    private String description;
    private String imageName;
    private Long size;
}
