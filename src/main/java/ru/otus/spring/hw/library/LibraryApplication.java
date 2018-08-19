package ru.otus.spring.hw.library;

import com.mongodb.MongoClient;
import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStyle;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.shell.jline.PromptProvider;
import ru.otus.spring.hw.library.repository.BookRepository;


@SpringBootApplication
@EnableMongoRepositories(basePackages = {"ru.otus.spring.hw.library.repository"})
public class LibraryApplication {


    public static void main(String[] args) {
//        Console.main(args);
        ConfigurableApplicationContext ctx = SpringApplication.run(LibraryApplication.class, args);
        BookRepository dao = ctx.getBean(BookRepository.class);

//        Book book = new Book();
//        book.setTitle("hobbit");
//        dao.save(book);
//
//        book.getAuthors().add(new Writer("test"));
//        dao.save(book);
//
//        System.out.println(book.getId());

    }

    @Bean
    public PromptProvider myPromptProvider() {
        return () -> new AttributedString("black-library:>", AttributedStyle.DEFAULT.foreground(AttributedStyle.GREEN));
    }

    @Bean
    public MongoClient mongo() {
        return new MongoClient("localhost");
    }

    @Bean
    public MongoTemplate mongoTemplate() {
        return new MongoTemplate(mongo(), "library");
    }


}
