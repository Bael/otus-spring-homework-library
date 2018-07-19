package ru.otus.spring.hw.library.dao;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import org.springframework.test.context.junit4.SpringRunner;
import ru.otus.spring.hw.library.domain.Book;
import ru.otus.spring.hw.library.domain.Writer;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = {
        ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false",
        InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false"
})
public class BookDAOImplTest {

    @Autowired
    BookDAO bookDAO;

    @Autowired
    WriterDAO writerDAO;

    private Writer writer;

    @Before
    public void setUp() {

        long id = writerDAO.createWriter(new Writer("John Tolkien"));
        writer = writerDAO.findById(id);
    }

    @Test
    public void createBook() {
        Book book = new Book();
        book.setTitle("hobbit");
        long id = bookDAO.createBook(book);

        Book bookFromDB = bookDAO.findById(id);

        Assert.assertEquals(id, bookFromDB.getId());
        Assert.assertEquals(book.getTitle(), bookFromDB.getTitle());

    }

    @Test
    public void updateBook() {
        Book book = new Book();
        book.setTitle("hobbit");
        long id = bookDAO.createBook(book);

        Book bookFromDB = bookDAO.findById(id);

        bookFromDB.getAuthors().add(writer);
        bookFromDB.setTitle("Hobbit 2");
        bookDAO.updateBook(bookFromDB);


        Book updateBook = bookDAO.findById(id);
        Assert.assertEquals(1, updateBook.getAuthors().size());
        Assert.assertEquals("John Tolkien", updateBook.getAuthors().get(0).getName());
        Assert.assertEquals("Hobbit 2", updateBook.getTitle());

    }

    @Test
    public void findAll() {
        int count = bookDAO.findAll().size();

        Book book = new Book();
        book.setTitle("hobbit");
        long id = bookDAO.createBook(book);
        List<Book> books = bookDAO.findAll();
        Assert.assertTrue(books.stream().anyMatch(book1 -> book1.getId() == id && book1.getTitle().equals(book.getTitle())));

        Assert.assertEquals(count + 1, books.size());

    }

    @Test
    public void findByTitle() {
        Book book = new Book();
        book.setTitle("Путешествие Геккельбери Финна");
        long id = bookDAO.createBook(book);
        List<Book> books = bookDAO.findByTitle("Геккельбери");
        Assert.assertTrue(books.stream().allMatch(book1 -> book1.getId() == id && book1.getTitle().equals(book.getTitle())));
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void deleteById() {
        Book book = new Book();
        book.setTitle("Путешествие Геккельбери Финна");
        long id = bookDAO.createBook(book);
        bookDAO.deleteById(id);
        bookDAO.findById(id);
    }
}