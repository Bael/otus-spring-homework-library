package ru.otus.spring.hw.library.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.hw.library.domain.Genre;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

@SuppressWarnings("JpaQueryApiInspection")
@Repository
public class GenreRepositoryImpl implements GenreRepository {

    @PersistenceContext
    EntityManager em;

    public GenreRepositoryImpl() {
    }

    @Override
    @Transactional
    public void createGenre(Genre genre) {
        em.persist(genre);
    }

    @Override
    @Transactional(readOnly = true)
    public Genre findById(long id) {
        return em.find(Genre.class, id);
    }

    @Override
    @Transactional
    public void updateGenre(Genre genre) {
        em.merge(genre);
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        Genre genre = em.find(Genre.class, id);
        em.remove(genre);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Genre> findGenres(String name) {
        TypedQuery<Genre> query = em.createQuery("select g from Genre g where g.name like :name ", Genre.class);
        query.setParameter("name", "%" + name + "%");
        return query.getResultList();
    }

    @Override
    @Transactional(readOnly = true)
    public Genre findGenre(String name) {
        TypedQuery<Genre> query = em.createQuery("select g from Genre g where g.name = :name ", Genre.class);
        query.setParameter("name", name);
        return query.getSingleResult();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Genre> findAll() {
        TypedQuery<Genre> query = em.createQuery("select g from Genre g ", Genre.class);
        return query.getResultList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Genre> genresByBookId(long bookId) {
        Query query = em.createNamedQuery("Genres.queryGenresByBookId", Genre.class)
                .setParameter("bookId", bookId);
        final List<Genre> resultList = query.getResultList();
        return resultList;
    }


    @Override
    @Transactional
    public Genre ensureGenre(String name) {
        TypedQuery<Genre> query = em.createQuery("select g from Genre g where g.name = :name ", Genre.class);
        query.setParameter("name", name);
        List<Genre> list = query.getResultList();

        Genre g;
        if (list.size() == 0) {
            g = new Genre(name);
            createGenre(g);
        } else {
            g = list.get(0);
        }
        return g;
    }


}
