package ru.itis.zagidullina.readl.repositories;

import ru.itis.zagidullina.readl.models.Book;
import ru.itis.zagidullina.readl.models.Chapter;

import java.util.List;
import java.util.Optional;

public interface ChapterRepository {
    List<Chapter> getChaptersOfBook(Book book);
    Optional<Chapter> findById(Integer id);
}
