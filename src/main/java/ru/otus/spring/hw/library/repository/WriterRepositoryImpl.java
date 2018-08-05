package ru.otus.spring.hw.library.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.hw.library.domain.Writer;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class WriterRepositoryImpl implements WriterRepository {

    @PersistenceContext
    private EntityManager em;

    public WriterRepositoryImpl() {
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void createWriter(Writer writer) {
        em.persist(writer);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateWriter(Writer writer) {
        em.merge(writer);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteById(long id) {
        em.remove(findById(id));
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true, noRollbackFor = Exception.class)
    public List<Writer> findByName(String name) {
        TypedQuery<Writer> query = em.createQuery("select w from Writer w where w.name like :name ", Writer.class);
        query.setParameter("name", "%" + name + "%");
        return query.getResultList();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true, noRollbackFor = Exception.class)
    public List<Writer> findAll() {
        TypedQuery<Writer> query = em.createQuery("select w from Writer w ", Writer.class);
        return query.getResultList();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public Writer findById(long id) {
        return em.find(Writer.class, id);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, noRollbackFor = Exception.class)
    public Writer ensureByName(String name) {
        Writer writer = findByExactName(name);
        if (writer == null) {
            writer = new Writer(name);
            createWriter(writer);
        }
        return writer;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public Writer findByExactName(String name) {
        TypedQuery<Writer> query = em.createQuery("select w from Writer w where w.name = :name ", Writer.class);
        query.setParameter("name", name);
        List<Writer> list = query.getResultList();
        if (list.size() == 0) {
            return null;
        } else {
            return list.get(0);
        }
    }


    @Override
    public List<Writer> authorsByGenre(String genre) {
        Query query = em.createNamedQuery("Writers.queryAuthorsByGenre", Writer.class);
        query.setParameter("genre", genre);
        final List<Writer> resultList = query.getResultList();
        return resultList;

    }


}
