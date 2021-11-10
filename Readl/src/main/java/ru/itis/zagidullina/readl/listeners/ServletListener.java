package ru.itis.zagidullina.readl.listeners;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import ru.itis.zagidullina.readl.repositories.*;
import ru.itis.zagidullina.readl.services.*;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.IOException;
import java.util.Properties;

@WebListener
public class ServletListener implements ServletContextListener{

    private ServletContext servletContext;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        servletContext = servletContextEvent.getServletContext();

        Properties properties = new Properties();
        try {
            properties.load(servletContext.getResourceAsStream("/WEB-INF/properties/db.properties"));
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }

        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(properties.getProperty("db.url"));
        hikariConfig.setDriverClassName(properties.getProperty("db.driver"));
        hikariConfig.setUsername(properties.getProperty("db.user"));
        hikariConfig.setPassword(properties.getProperty("db.password"));
        hikariConfig.setMaximumPoolSize(Integer.parseInt(properties.getProperty("db.hikari.pool-size")));

        HikariDataSource dataSource = new HikariDataSource(hikariConfig);
        servletContext.setAttribute("dataSource", dataSource);

        String storagePath = "C:\\files\\";
        servletContext.setAttribute("storagePath", storagePath);

        Validator validator = new Validator();
        servletContext.setAttribute("validator", validator);

        AccountsRepository accountsRepository = new AccountsRepositoryJdbcTemplateImpl(dataSource);
        ChapterRepository chapterRepository = new ChapterRepositoryImpl(dataSource);
        CommentsRepository commentsRepository = new CommentsRepositoryImpl(dataSource);
        ReviewsRepository reviewsRepository = new ReviewsRepositoryImpl(dataSource);
        GenreRepository genreRepository = new GenreRepositoryImpl(dataSource);

        BookRepository bookRepository = new BookRepositoryJdbcTemplateImpl(dataSource, accountsRepository, chapterRepository, commentsRepository, reviewsRepository, genreRepository);
        FavouriteRepository favouriteRepository = new FavouriteRepositoryImpl(dataSource, bookRepository);

        VkService vkService = new VkServiceImpl(accountsRepository);
        servletContext.setAttribute("vkService", vkService);

        AuthService authService = new AuthServiceImpl(accountsRepository, vkService, validator);
        servletContext.setAttribute("authService", authService);

        AccountsService usersService = new AccountsServiceImpl(accountsRepository, favouriteRepository);
        servletContext.setAttribute("usersService", usersService);

        BookService bookService = new BookServiceImpl(bookRepository, genreRepository, validator, storagePath);
        servletContext.setAttribute("bookService", bookService);

        RateService rateService = new RateServiceImpl(reviewsRepository, accountsRepository);
        servletContext.setAttribute("rateService", rateService);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        HikariDataSource hikariDataSource = (HikariDataSource) servletContextEvent.getServletContext().getAttribute("dataSource");

        hikariDataSource.close();
    }
}
