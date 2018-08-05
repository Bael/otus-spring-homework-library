package ru.otus.spring.hw.library;

import org.h2.tools.Console;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import ru.otus.spring.hw.library.domain.Book;
import ru.otus.spring.hw.library.domain.Writer;
import ru.otus.spring.hw.library.repository.BookRepository;

import java.sql.SQLException;


@SpringBootApplication
@EnableTransactionManagement
public class LibraryApplication {


    public static void main(String[] args) throws SQLException {
        Console.main(args);
        ConfigurableApplicationContext ctx = SpringApplication.run(LibraryApplication.class, args);
        BookRepository dao = ctx.getBean(BookRepository.class);

        Book book = new Book();
        book.setTitle("hobbit");
        dao.createBook(book);

        book.getAuthors().add(new Writer("test"));
        dao.updateBook(book);

        System.out.println(book.getId());

    }
/*
    @Bean
    public PromptProvider myPromptProvider() {
        return () -> new AttributedString("black-library:>", AttributedStyle.DEFAULT.foreground(AttributedStyle.GREEN));
    }
    */
}
