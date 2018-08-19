package ru.otus.spring.hw.library.repository;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
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
    WriterRepository writerRepository;

    @Autowired
    MongoTemplate mongoTemplate;

    @Before
    public void setUp() {

        // TODO - REMOVE. (reason - de.flapdoodle.embed.mongo does not work)
        mongoTemplate.dropCollection(Writer.class);

        writerRepository.findAll().forEach(writer -> writerRepository.deleteById(writer.getId()));

        writerRepository.save(new Writer("Fedor Dostoevsky"));
        writerRepository.save(new Writer("Dan Abnet"));
        writerRepository.save(new Writer("Linda Gamilton"));
        writerRepository.save(new Writer("Jim Butcher"));
        writerRepository.save(new Writer("Ron Butcher"));
        writerRepository.save(new Writer("Roger Gelyazny"));
        writerRepository.save(new Writer("Ray Bradberry"));

    }


    @Test
    public void updateWriter() throws Exception {
        Writer w = new Writer("Tendryakov");
        writerRepository.save(w);

        Writer writer = writerRepository.findByName("Tendryakov");
        writer.setName("Tennesy");
        writerRepository.save(writer);

        Writer updatedWriter = writerRepository.findById(w.getId()).orElseThrow(Exception::new);
        Assert.assertEquals(updatedWriter.getName(), writer.getName());

    }

    @Test
    public void deleteWriter() {
        writerRepository.findAll().forEach(writer -> writerRepository.deleteById(writer.getId()));
        Assert.assertFalse(writerRepository.findAll().iterator().hasNext());


    }

    @Test
    public void findByName() {
        Assert.assertEquals(2, writerRepository.findByNameLike("Butcher").size());
    }

    @Test
    public void findByExactName() {
        Assert.assertNotNull(writerRepository.findByName("Roger Gelyazny"));
    }


}