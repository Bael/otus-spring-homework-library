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
import ru.otus.spring.hw.library.domain.Writer;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = {
        ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false",
        InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false"
})
public class WriterRepositoryImplTest {


    @Autowired
    WriterRepository writerDAO;

    @Before
    public void setUp() {
        writerDAO.findAll().forEach(writer -> writerDAO.deleteById(writer.getId()));

        writerDAO.save(new Writer("Fedor Dostoevsky"));
        writerDAO.save(new Writer("Dan Abnet"));
        writerDAO.save(new Writer("Linda Gamilton"));
        writerDAO.save(new Writer("Jim Butcher"));
        writerDAO.save(new Writer("Ron Butcher"));
        writerDAO.save(new Writer("Roger Gelyazny"));
        writerDAO.save(new Writer("Ray Bradberry"));

    }


    @Test
    public void updateWriter() throws Exception {
        Writer w = new Writer("Tendryakov");
        writerDAO.save(w);
        long id = w.getId();
        Writer writer = new Writer(id, "Tenkerry");
        writerDAO.save(writer);
        Writer updatedWriter = writerDAO.findById(id).orElseThrow(Exception::new);
        Assert.assertEquals(updatedWriter.getName(), writer.getName());

    }

    @Test
    public void deleteWriter() {
        writerDAO.findAll().forEach(writer -> writerDAO.deleteById(writer.getId()));
        Assert.assertFalse(writerDAO.findAll().iterator().hasNext());


    }

    @Test
    public void findByName() {
        Assert.assertEquals(2, writerDAO.findByNameLike("Butcher").size());
    }

    @Test
    public void findByExactName() {
        Assert.assertNotNull(writerDAO.findByName("Roger Gelyazny"));
    }


}