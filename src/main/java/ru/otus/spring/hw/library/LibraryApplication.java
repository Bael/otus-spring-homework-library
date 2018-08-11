package ru.otus.spring.hw.library;

import org.h2.tools.Console;
import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStyle;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.shell.jline.PromptProvider;
import ru.otus.spring.hw.library.domain.Book;
import ru.otus.spring.hw.library.domain.Writer;
import ru.otus.spring.hw.library.repository.BookRepository;

import java.sql.SQLException;



@SpringBootApplication
@EnableJpaRepositories
public class LibraryApplication {


    public static void main(String[] args) throws SQLException {
        Console.main(args);
        ConfigurableApplicationContext ctx = SpringApplication.run(LibraryApplication.class, args);
        BookRepository dao = ctx.getBean(BookRepository.class);

        Book book = new Book();
        book.setTitle("hobbit");
        dao.save(book);

        book.getAuthors().add(new Writer("test"));
        dao.save(book);

        System.out.println(book.getId());

    }

    @Bean
    public PromptProvider myPromptProvider() {
        return () -> new AttributedString("black-library:>", AttributedStyle.DEFAULT.foreground(AttributedStyle.GREEN));
    }

}
