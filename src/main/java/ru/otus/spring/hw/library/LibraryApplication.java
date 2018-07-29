package ru.otus.spring.hw.library;

import org.h2.tools.Console;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import ru.otus.spring.hw.library.dao.BookDAO;
import ru.otus.spring.hw.library.domain.Book;

import java.sql.SQLException;


@SpringBootApplication
public class LibraryApplication {


    public static void main(String[] args) throws SQLException {
        Console.main(args);
        ConfigurableApplicationContext ctx = SpringApplication.run(LibraryApplication.class, args);
        BookDAO dao = ctx.getBean(BookDAO.class);
        Book book = new Book();
        book.setTitle("hobbit");
        long id = dao.createBook(book);
        System.out.println(id);

    }
/*
    @Bean
    public PromptProvider myPromptProvider() {
        return () -> new AttributedString("black-library:>", AttributedStyle.DEFAULT.foreground(AttributedStyle.GREEN));
    }
    */
}
