package ru.itis.zagidullina.readl.listeners;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.itis.zagidullina.readl.config.ApplicationConfig;
import ru.itis.zagidullina.readl.repositories.AccountsRepository;
import ru.itis.zagidullina.readl.repositories.AccountsRepositoryJdbcTemplateImpl;
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

        AccountsRepository accountsRepository = new AccountsRepositoryJdbcTemplateImpl(springContext.getBean(HikariDataSource.class));

        AuthService authService = new AuthServiceImpl(accountsRepository);
        servletContext.setAttribute("authService", authService);
        UsersService usersService = new UsersServiceImpl(accountsRepository);
        servletContext.setAttribute("usersService", usersService);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        HikariDataSource hikariDataSource = springContext.getBean(HikariDataSource.class);

        hikariDataSource.close();
    }
}
