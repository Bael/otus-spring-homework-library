package ru.otus.spring.hw.library.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
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
    @Transactional(propagation = Propagation.REQUIRED)
    public void createBook(Book book) {
        em.persist(book);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Book findById(long id) {
        return em.find(Book.class, id);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true, noRollbackFor = Exception.class)
    public List<Book> findByTitleLike(String title) {
        TypedQuery<Book> query = em.createQuery("select b from Book b where b.title like :title ", Book.class);
        query.setParameter("title", "%" + title + "%");
        return query.getResultList();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true, noRollbackFor = Exception.class)
    public List<Book> findByTitle(String title) {
        TypedQuery<Book> query = em.createQuery("select b from Book b where b.title = :title ", Book.class);
        query.setParameter("title", title);
        return query.getResultList();
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true, noRollbackFor = Exception.class)
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
        TypedQuery<Book> query = em.createQuery("select b from Book b join Genre g where g.name = :genre", Book.class);
        query.setParameter("genre", genre);
        return query.getResultList();
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteById(long id) {

        Book book = findById(id);
        em.remove(book);
    }

    @Override
    @Transactional
    public void updateBook(Book book) {
        em.merge(book);

    }


    /*




    private void deleteGenres(long bookId) {
        jdbc.update("delete from BOOK_GENRE  where bookid = :id", Collections.singletonMap("id", bookId));
    }

    private void insertGenres(long bookId, List<Genre> genres) {
        genres.forEach(genre -> insertGenre(bookId, genre.getId()));
    }

    private void insertGenre(long bookId, long genreId) {
        final HashMap<String, Object> params = new HashMap<>();
        params.put("bookId", bookId);
        params.put("genreId", genreId);
        jdbc.update("Insert into BOOK_GENRE (bookid, genreid) values (:bookId, :genreId)", params);
    }

    private void ensureGenres(long bookId, List<Genre> genres) {
        deleteGenres(bookId);
        insertGenres(bookId, genres);
    }


    private void deleteAuthors(long bookId) {
        jdbc.update("delete from BOOK_AUTHOR where bookid = :id", Collections.singletonMap("id", bookId));
    }

    private void insertAuthors(long bookId, List<Writer> authors) {
        for (Writer author : authors) {
            insertAuthor(bookId, author.getId());
        }

    }

    private void insertAuthor(long bookId, long authorId) {
        final HashMap<String, Object> params = new HashMap<>();
        params.put("bookId", bookId);
        params.put("authorId", authorId);
        jdbc.update("Insert into BOOK_AUTHOR (bookid, authorId) values (:bookId, :authorId)", params);
    }

    private void ensureAuthors(long bookId, List<Writer> authors) {
        deleteAuthors(bookId);
        insertAuthors(bookId, authors);
    }


    @Override
    public List<Book> findAll() {
        return jdbc.query(SELECT_STATEMENT, new BookMapper());
    }


    @Override
    public List<Book> findByTitle(String title) {
        final Map<String, Object> params = Collections.singletonMap("title", "%" + title + "%");
        return jdbc.query(SELECT_STATEMENT + "where title like :title", params, new BookMapper());
    }

    @Override
    public List<Book> findByAuthorName(String name) {
        return jdbc.query("select books.* from books join book_author as ba on books.id = ba.bookid  "
                        + " join writers on writers.id = ba.authorid where writers.name like :authorName",
                Collections.singletonMap("authorName", name), new BookMapper());

    }

    @Override
    public List<Book> findByGenreName(String name) {
        return jdbc.query("select books.* from books join book_genre ga on books.id = ga.bookid  "
                        + " join genres on genres.id = ga.genreid where genres.name like :genreName",
                Collections.singletonMap("genreName", name), new BookMapper());
    }

    @Override
    public Book findById(long id) {
        return jdbc.queryForObject(SELECT_STATEMENT + "where id = :id ",
                Collections.singletonMap("id", id), new BookMapper());
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        jdbc.update("delete from books where id = :id ", Collections.singletonMap("id", id));
    }

    @Override
    @Transactional
    public void updateBook(Book book) {

        final HashMap<String, Object> params = new HashMap<>();
        params.put("title", book.getTitle());
        params.put("id", book.getId());
        jdbc.update("update books set title = :title where id = :id", params);
        System.out.println(book.getAuthors());
        ensureAuthors(book.getId(), book.getAuthors());
        ensureGenres(book.getId(), book.getGenres());
    }

*/
}
