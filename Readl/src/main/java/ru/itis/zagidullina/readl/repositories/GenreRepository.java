package ru.itis.zagidullina.readl.repositories;

import ru.itis.zagidullina.readl.models.Genre;

import java.util.List;

public interface GenreRepository {
    List<Genre> findAll();
    List<Genre> findGenresOfBook(Integer id);
}
