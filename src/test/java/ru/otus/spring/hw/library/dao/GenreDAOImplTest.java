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
import ru.otus.spring.hw.library.domain.Genre;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = {
        ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false",
        InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false"
})
public class GenreDAOImplTest {

    @Autowired
    GenreDAO genreDAO;

    @Test
    public void createGenre() {
        Genre newGenre = new Genre("science fiction");
        long id = genreDAO.createGenre(newGenre);
        Genre genreFromDB = genreDAO.findById(id);
        Assert.assertEquals(genreFromDB.getName(), newGenre.getName());

    }

    @Test
    public void updateGenre() {
        Genre newGenre = new Genre("science fiction");

        long id = genreDAO.createGenre(newGenre);
        Genre genreFromDB = genreDAO.findById(id);
        Assert.assertEquals(genreFromDB.getName(), newGenre.getName());

        Genre changed = new Genre(id, "new wave fiction");
        genreDAO.updateGenre(changed);
        genreFromDB = genreDAO.findById(id);
        Assert.assertEquals(genreFromDB.getName(), changed.getName());

    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void deleteGenre() {
        Genre newGenre = new Genre("science fiction");
        long id = genreDAO.createGenre(newGenre);
        genreDAO.deleteById(id);
        genreDAO.findById(id);
    }

    @Test
    public void findGenres() {
        Assert.assertEquals(2, genreDAO.findGenres("saga").size());
    }


    @Test
    public void genresByBookId() {

        List<Genre> list = genreDAO.genresByBookId(100);
        System.out.println(list);

    }

    @Before
    public void setUp() {
        genreDAO.findAll().forEach(genre -> genreDAO.deleteById(genre.getId()));

        genreDAO.createGenre(new Genre("classical"));
        genreDAO.createGenre(new Genre("horror"));
        genreDAO.createGenre(new Genre("saga"));
        genreDAO.createGenre(new Genre("epic saga"));
    }
}