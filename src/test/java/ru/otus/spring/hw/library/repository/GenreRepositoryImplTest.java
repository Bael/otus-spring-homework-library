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
import ru.otus.spring.hw.library.domain.Genre;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = {
        ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false",
        InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false"
})
public class GenreRepositoryImplTest {

    @Autowired
    GenreRepository genreRepository;

    @Test
    public void createGenre() throws Exception {
        Genre newGenre = new Genre("science fiction");
        genreRepository.save(newGenre);
        Genre genreFromDB = genreRepository.findById(newGenre.getId()).orElseThrow(Exception::new);
        Assert.assertEquals(genreFromDB.getName(), newGenre.getName());

    }

    @Test
    public void updateGenre() throws Exception {
        Genre newGenre = new Genre("science fiction");

        genreRepository.save(newGenre);
        Genre genreFromDB = genreRepository.findById(newGenre.getId()).orElseThrow(Exception::new);
        Assert.assertEquals(genreFromDB.getName(), newGenre.getName());

        Genre changed = new Genre(newGenre.getId(), "new wave fiction");
        genreRepository.save(changed);
        genreFromDB = genreRepository.findById(newGenre.getId()).orElseThrow(Exception::new);
        Assert.assertEquals(genreFromDB.getName(), changed.getName());

    }

    @Test
    public void deleteGenre() {
        Genre newGenre = new Genre("science fiction");
        genreRepository.save(newGenre);
        genreRepository.deleteById(newGenre.getId());

        Assert.assertFalse(genreRepository.findById(newGenre.getId()).isPresent());
    }

    @Test
    public void findGenres() {
        Assert.assertEquals(2, genreRepository.findGenresByNameLike("%saga%").size());
    }


    @Test
    public void genresByBookId() {

        List<Genre> list = genreRepository.genresByBookId(100);
        System.out.println(list);

    }

    @Before
    public void setUp() {
        genreRepository.findAll().forEach(genre -> genreRepository.deleteById(genre.getId()));

        genreRepository.save(new Genre("classical"));
        genreRepository.save(new Genre("horror"));
        genreRepository.save(new Genre("saga"));
        genreRepository.save(new Genre("epic saga"));
    }
}