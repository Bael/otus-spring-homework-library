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

        writerDAO.createWriter(new Writer("Fedor Dostoevsky"));
        writerDAO.createWriter(new Writer("Dan Abnet"));
        writerDAO.createWriter(new Writer("Linda Gamilton"));
        writerDAO.createWriter(new Writer("Jim Butcher"));
        writerDAO.createWriter(new Writer("Ron Butcher"));
        writerDAO.createWriter(new Writer("Roger Gelyazny"));
        writerDAO.createWriter(new Writer("Ray Bradberry"));

    }


    @Test
    public void updateWriter() {
        Writer w = new Writer("Tendryakov");
        writerDAO.createWriter(w);
        long id = w.getId();
        Writer writer = new Writer(id, "Tenkerry");
        writerDAO.updateWriter(writer);
        Writer updatedWriter = writerDAO.findById(id);
        Assert.assertEquals(updatedWriter.getName(), writer.getName());

    }

    @Test
    public void deleteWriter() {
        writerDAO.findAll().forEach(writer -> writerDAO.deleteById(writer.getId()));
        Assert.assertEquals(0, writerDAO.findAll().size());


    }

    @Test
    public void findByName() {
        Assert.assertEquals(2, writerDAO.findByName("Butcher").size());
    }

    @Test
    public void findByExactName() {
        Assert.assertNotNull(writerDAO.findByExactName("Roger Gelyazny"));
    }


}