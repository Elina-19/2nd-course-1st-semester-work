package ru.itis.zagidullina.readl.listeners;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.itis.zagidullina.readl.config.ApplicationConfig;
import ru.itis.zagidullina.readl.repositories.AccountsRepository;
import ru.itis.zagidullina.readl.repositories.AccountsRepositoryJdbcTemplateImpl;
import ru.itis.zagidullina.readl.repositories.BookRepository;
import ru.itis.zagidullina.readl.repositories.BookRepositoryJdbcTemplateImpl;
import ru.itis.zagidullina.readl.services.*;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ServletListener implements ServletContextListener{

    private ApplicationContext springContext;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        springContext = new AnnotationConfigApplicationContext(ApplicationConfig.class);
        ServletContext servletContext = servletContextEvent.getServletContext();
        servletContext.setAttribute("springContext", springContext);

        String storagePath = "C:\\files\\";
        servletContext.setAttribute("storagePath", storagePath);

        Validator validator = new Validator();
        servletContext.setAttribute("validator", validator);

        AccountsRepository accountsRepository = new AccountsRepositoryJdbcTemplateImpl(springContext.getBean(HikariDataSource.class));
        BookRepository bookRepository = new BookRepositoryJdbcTemplateImpl(springContext.getBean(HikariDataSource.class));

        AuthService authService = new AuthServiceImpl(accountsRepository, validator);
        servletContext.setAttribute("authService", authService);

        AccountsService usersService = new AccountsServiceImpl(accountsRepository);
        servletContext.setAttribute("usersService", usersService);

        BookService bookService = new BookServiceImpl(bookRepository, validator, storagePath);
        servletContext.setAttribute("bookService", bookService);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        HikariDataSource hikariDataSource = springContext.getBean(HikariDataSource.class);

        hikariDataSource.close();
    }
}
