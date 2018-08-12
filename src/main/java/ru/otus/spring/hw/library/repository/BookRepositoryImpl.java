package ru.otus.spring.hw.library.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.hw.library.domain.Book;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

@SuppressWarnings("JpaQueryApiInspection")
@Repository
public class BookRepositoryImpl implements BookRepository {

    @PersistenceContext
    private EntityManager em;

    private GenreRepository genreRepository;
    private WriterRepository writerRepository;


    public BookRepositoryImpl(GenreRepository genreRepository, WriterRepository writerRepository) {
        this.genreRepository = genreRepository;
        this.writerRepository = writerRepository;
    }

    @Override
    public void createBook(Book book) {
        em.persist(book);
    }

    @Override
    public Book findById(long id) {
        return em.find(Book.class, id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Book> findByTitleLike(String title) {
        TypedQuery<Book> query = em.createQuery("select b from Book b where b.title like :title ", Book.class);
        query.setParameter("title", "%" + title + "%");
        return query.getResultList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Book> findByTitle(String title) {
        TypedQuery<Book> query = em.createQuery("select b from Book b where b.title = :title ", Book.class);
        query.setParameter("title", title);
        return query.getResultList();
    }


    @Override
    @Transactional(readOnly = true)
    public List<Book> findAll() {
        TypedQuery<Book> query = em.createQuery("select b from Book b", Book.class);
        return query.getResultList();
    }

    @Override
    public List<Book> findByAuthorName(String name) {
        Query query = em.createNamedQuery("Books.queryBooksByAuthorName", Book.class)
                .setParameter("authorName", name);
        final List<Book> resultList = query.getResultList();
        return resultList;
    }

    @Override
    public List<Book> findByGenreName(String genre) {
        TypedQuery<Book> query = em.createQuery("select b from Book b join b.genres g where g.name = :genre", Book.class);
        query.setParameter("genre", genre);
        return query.getResultList();
    }


    @Override
    @Transactional
    public void deleteById(long id) {

        Book book = findById(id);
        em.remove(book);
    }

    @Override
    @Transactional
    public void updateBook(Book book) {
        em.merge(book);

    }


}
