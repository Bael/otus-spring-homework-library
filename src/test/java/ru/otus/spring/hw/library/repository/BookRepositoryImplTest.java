package ru.otus.spring.hw.library.repository;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import ru.otus.spring.hw.library.domain.Book;
import ru.otus.spring.hw.library.domain.Genre;
import ru.otus.spring.hw.library.domain.Writer;
import ru.otus.spring.hw.library.exceptions.NotFoundException;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.springframework.test.annotation.DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = {
        ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false",
        InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false"
} /*, classes = {
        LibraryApplication.class,
        TestConfig.class // <--- Don't forget THIS
} */)
@DataMongoTest
@ActiveProfiles("test")
@DirtiesContext(classMode = BEFORE_EACH_TEST_METHOD)
public class BookRepositoryImplTest {

    @Autowired
    MongoTemplate mongoTemplate;

//    @Autowired
//    WriterService writerService;

    @Autowired
    BookRepository bookRepository;
    @Autowired
    WriterRepository writerRepository;

    public BookRepositoryImplTest() {
    }

    @Before
    public void setUp() {
        // TODO - REMOVE. (reason - de.flapdoodle.embed.mongo does not work)
        mongoTemplate.dropCollection(Book.class);
        mongoTemplate.dropCollection(Writer.class);


    }

    @Test
    public void createBook() {


        Book book = new Book();
        book.setTitle("hobbit");

        Writer writer = new Writer("arthur conan doyle");

        writerRepository.save(writer);


        book.getAuthors().add(writer);

        bookRepository.save(book);
        System.out.println(book.getId());

        Book bookFromDB = bookRepository.findByTitle(book.getTitle()).orElseThrow(() -> new NotFoundException("Not founded"));

        Assert.assertEquals(book.getId(), bookFromDB.getId());
        Assert.assertEquals(book.getTitle(), bookFromDB.getTitle());

    }


    @Test
    public void findAuthorsByGenre() {
        Book book = new Book();
        book.setTitle("Путешествие Геккельбери Финна");
        book.addGenre(new Genre("drama"));
        book.addGenre(new Genre("fiction"));

        Writer writer = new Writer("doyle");
        writerRepository.save(writer);
        book.getAuthors().add(writer);
        bookRepository.save(book);

        List<Book> books = bookRepository.findByGenresContaining("drama");
        //System.out.println(books);
        final Set<Writer> writers = new HashSet<>();
        books.stream().map(book3 -> book3.getAuthors()).forEach(writers1 -> writers.addAll(writers1));
        System.out.println(writers.iterator().next());
        Assert.assertEquals(true, writers.stream().anyMatch(writer1 -> writer1.getName().equals("doyle")));
    }


    @Test
    public void findByTitle() {
        Book book = new Book();
        book.setTitle("Путешествие Геккельбери Финна");
        bookRepository.save(book);
        List<Book> books = bookRepository.findByTitleLike("Геккельбери");

        //books.forEach(book1 -> System.out.println(book.getId() + " " + book.getTitle()));
        Assert.assertEquals("Ожидалось найти одну книгу", 1, books.size());
        Assert.assertTrue(books.stream().allMatch(book1 -> book1.getTitle().equals(book.getTitle())));
    }

//    @Test
//    public void findAll() {
//        int count = bookRepository.findAll().size();

//        Book book = new Book();
//        book.setTitle("hobbit");
//        bookRepository.createBook(book);
//
//        List<Book> books = bookRepository.findAll();
//
//        Assert.assertEquals(count + 1, books.size());
//
//    }

    @Test
    public void updateBook() throws Exception {
        Book book = new Book();
        book.setTitle("hobbit");
        bookRepository.save(book);

        Book bookFromDB = bookRepository.findById(book.getId()).orElse(new Book());

        Writer writer = new Writer("John Tolkien");
        writerRepository.save(writer);

        bookFromDB.addAuthor(writer);

        bookFromDB.setTitle("Hobbit 2");
        bookRepository.save(bookFromDB);
        Book updateBook = bookRepository.findById(book.getId()).orElseThrow(Exception::new);

        Set<Writer> authors = bookFromDB.getAuthors();

        Assert.assertEquals(1, authors.size());
        Assert.assertEquals("John Tolkien", authors.iterator().next().getName());
        Assert.assertEquals("Hobbit 2", updateBook.getTitle());

    }

    @Test
    public void findAll() {
        int count = bookRepository.findAll().size();

        Book book = new Book();
        book.setTitle("hobbit");
        bookRepository.save(book);
        List<Book> books = bookRepository.findAll();
        Assert.assertTrue(books.stream().anyMatch(book1 -> book1.getTitle().equals(book.getTitle())));

        Assert.assertEquals(count + 1, books.size());

    }

    @Test
    public void deleteById() {
        Book book = new Book();
        book.setTitle("Путешествие Геккельбери Финна");
        bookRepository.save(book);
        bookRepository.deleteById(book.getId());
        Assert.assertFalse(bookRepository.findById(book.getId()).isPresent());
    }


}