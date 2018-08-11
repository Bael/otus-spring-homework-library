package ru.otus.spring.hw.library.repository;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.hw.library.domain.Book;
import ru.otus.spring.hw.library.domain.Writer;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = {
        ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false",
        InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false"
})
@EnableTransactionManagement
@Transactional
public class BookRepositoryImplTest {

    @Autowired
    BookRepository bookDAO;

    @Autowired
    WriterRepository writerDAO;

    private Writer writer;

    @Before
    public void setUp() throws Exception {

        Writer w = new Writer("John Tolkien");
        writerDAO.save(w);

        long id = w.getId();
        writer = writerDAO.findById(id).orElseThrow(Exception::new);
    }

    @Test
    public void createBook() {
        Book book = new Book();
        book.setTitle("hobbit");
        book.getAuthors().add(new Writer("arthur conan doyle"));

        bookDAO.save(book);
        System.out.println(book.getId());

        Book bookFromDB = bookDAO.findById(book.getId()).orElseThrow(EntityNotFoundException::new);

        Assert.assertEquals(book.getId(), bookFromDB.getId());
        Assert.assertEquals(book.getTitle(), bookFromDB.getTitle());

    }


    @Test
    public void findByTitle() {
        Book book = new Book();
        book.setTitle("Путешествие Геккельбери Финна");
        bookDAO.save(book);
        List<Book> books = bookDAO.findByTitleLike("%Геккельбери%");

        books.forEach(book1 -> System.out.println(book.getId() + " " + book.getTitle()));
        Assert.assertEquals("Ожидалось найти одну книгу", 1, books.size());
        Assert.assertTrue(books.stream().allMatch(book1 -> book1.getId() == book.getId() && book1.getTitle().equals(book.getTitle())));
    }

//    @Test
//    public void findAll() {
//        int count = bookDAO.findAll().size();
//
//        Book book = new Book();
//        book.setTitle("hobbit");
//        bookDAO.createBook(book);
//
//        List<Book> books = bookDAO.findAll();
//
//        Assert.assertEquals(count + 1, books.size());
//
//    }

    @Test
    public void updateBook() throws Exception {
        Book book = new Book();
        book.setTitle("hobbit");
        bookDAO.save(book);

        Book bookFromDB = bookDAO.findById(book.getId()).orElse(new Book());

        bookFromDB.getAuthors().add(writer);

        bookFromDB.setTitle("Hobbit 2");
        bookDAO.save(bookFromDB);
        Book updateBook = bookDAO.findById(book.getId()).orElseThrow(Exception::new);
        Set<Writer> authors = book.getAuthors();

        Assert.assertEquals(1, authors.size());
        Assert.assertEquals("John Tolkien", authors.iterator().next().getName());
        Assert.assertEquals("Hobbit 2", updateBook.getTitle());

    }

    @Test
    public void findAll() {
        int count = bookDAO.findAll().size();

        Book book = new Book();
        book.setTitle("hobbit");
        bookDAO.save(book);
        List<Book> books = bookDAO.findAll();
        Assert.assertTrue(books.stream().anyMatch(book1 -> book1.getId() == book.getId() && book1.getTitle().equals(book.getTitle())));

        Assert.assertEquals(count + 1, books.size());

    }

    @Test
    public void deleteById() {
        Book book = new Book();
        book.setTitle("Путешествие Геккельбери Финна");
        bookDAO.save(book);
        bookDAO.deleteById(book.getId());
        Assert.assertFalse(bookDAO.findById(book.getId()).isPresent());
    }


}