package ru.otus.spring.hw.library.ui;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.shell.Shell;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import org.springframework.test.context.junit4.SpringRunner;
import ru.otus.spring.hw.library.repository.BookRepository;
import ru.otus.spring.hw.library.repository.WriterRepository;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = {
        InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false",
        ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false"
})
public class UIShellTest {


    @Autowired
    private Shell shell;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    WriterRepository writerRepository;

    @Before
    public void setUp() {
        // TODO - REMOVE. (reason - de.flapdoodle.embed.mongo does not work)
        writerRepository.deleteAll();
        bookRepository.deleteAll();
    }

    @Test
    public void contextLoads() {
        Object help = shell.evaluate(() -> "help");
        assertNotNull(help);
        System.out.println(help);
    }

    @Test
    public void bookCreated() {
        String result = shell.evaluate(() -> "create-book giperion fiction,drama simmons,abnett,petrov").toString();
        assertNotNull(result);

        Assert.assertTrue(result.contains("giperion"));
        Assert.assertTrue(result.contains("drama"));
        Assert.assertTrue(result.contains("abnett"));
    }

    @Test
    public void bookUpdated() {
        String result = shell.evaluate(() -> "create-book giperion fiction,drama simmons,abnett,petrov").toString();
        assertNotNull(result);

        result = shell.evaluate(() -> "update-book giperion endemion fiction simmons,abnett,pertov").toString();
        assertNotNull(result);

        Assert.assertFalse(result.contains("giperion"));
        Assert.assertTrue(result.indexOf("endemion") > 0);
    }

    @Test
    public void bookDeleted() {
        String result = shell.evaluate(() -> "create-book giperion fiction,drama simmons,abnett,petrov").toString();
        assertNotNull(result);

        result = shell.evaluate(() -> "delete-book giperion").toString();
        assertNotNull(result);

        Assert.assertFalse(result.contains("giperion"));
        Assert.assertFalse(result.contains("fiction"));
        Assert.assertFalse(result.contains("simmons"));

    }

    @Test
    public void authorsByGenre() {
        shell.evaluate(() -> "create-book giperion fiction,drama simmons,petrov");
        shell.evaluate(() -> "create-book pariah drama abnett");

        String result = shell.evaluate(() -> "show-authors-by-genre drama").toString();

        System.out.println(result);
        Assert.assertTrue(result.contains("simmons"));
        Assert.assertTrue(result.contains("abnett"));
        Assert.assertTrue(result.contains("petrov"));

        result = shell.evaluate(() -> "show-authors-by-genre fiction").toString();

        Assert.assertTrue(result.contains("simmons"));
        Assert.assertFalse(result.contains("abnett"));
        Assert.assertTrue(result.contains("petrov"));


    }
}

