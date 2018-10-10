package ru.otus.spring.hw.library.ui;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.table.*;
import org.springframework.stereotype.Service;
import ru.otus.spring.hw.library.domain.Book;
import ru.otus.spring.hw.library.domain.Comment;
import ru.otus.spring.hw.library.domain.Genre;
import ru.otus.spring.hw.library.domain.Writer;
import ru.otus.spring.hw.library.exceptions.NotFoundException;
import ru.otus.spring.hw.library.service.BookService;
import ru.otus.spring.hw.library.service.WriterService;

import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.shell.table.CellMatchers.at;

@Service
@ShellComponent
public class UIShell {


    private static final int TERMINAL_WIDTH = 80;
    private BookService bookService;
    private WriterService writerService;

    public UIShell(BookService bookService, WriterService writerService) {
        this.bookService = bookService;
        this.writerService = writerService;
    }


    @ShellMethod(value = "Show all books", key = {"show-books", "все-книги"})
    private String showBooks() {
        return getBooksTable(bookService.findAll());
    }


    @ShellMethod(value = "Create new book. Args are: name of book, genres (separated by comma) and names of authors (separated by comma).", key = {"create-book", "создать-книгу"})
    private String createBook(@Size(min = 1) String nameOfBook, @Size(min = 1) String genres, @Size(min = 1) String authors) {

        Set<String> genresList = getGenresList(genres);
        Set<String> writersList = getWritersList(authors);

        bookService.createBook(new Book(nameOfBook), genresList, writersList);
        return getBooksTable(bookService.findAll());

    }

    private Set<String> getWritersList(String writers) {
        return Arrays.stream(writers.split(","))
                .collect(Collectors.toSet());
    }

    private Set<String> getGenresList(String genres) {
        return Arrays.stream(genres.split(","))
                .collect(Collectors.toSet());

    }


    @ShellMethod(value = "Update book. Args are: old name of book, new name of book, " +
            "new genres (separated by comma) and new names of authors (separated by comma).",
            key = {"update-book", "обновить-книгу"})
    private String updateBookByTitle(@Size(min = 1) String nameOfBook, @Size(min = 1) String newNameOfBook,
                                     @Size(min = 1) String genres, @Size(min = 1) String authors) throws NotFoundException {

        Set<String> genresNames = getGenresList(genres);
        Set<String> writersNames = getWritersList(authors);

        bookService.updateBook(nameOfBook, newNameOfBook, genresNames, writersNames);
        return getBooksTable(bookService.findAll());
    }


    @ShellMethod(value = "Delete book. Args are: book name", key = {"delete-book", "удалить-книгу"})
    private String deleteBook(@Size(min = 1) String nameOfBook) {
        bookService.deleteBook(nameOfBook);
        return getBooksTable(bookService.findAll());
    }

    @ShellMethod(value = "Show books by author. Args are: author's name", key = {"show-books-by-author", "книги-автора"})
    private String showBooksByAuthor(@Size(min = 1) String nameOfAuthor) {
        return getBooksTable(new ArrayList<>(
                bookService.findByAuthorName(nameOfAuthor))
        );
    }

    @ShellMethod(value = "Show books by genre. Args are: genre name", key = {"show-books-by-genre", "книги-по-жанру"})
    private String showBooksByGenre(@Size(min = 1) String nameOfGenre) {

        return getBooksTable(new ArrayList<>(
                bookService.findByGenreName(nameOfGenre)
        ));
    }


    @ShellMethod(value = "Show authors by genre. Args are: genre name", key = {"show-authors-by-genre", "авторы-жанра"})
    private String showAuthorsByGenre(@Size(min = 1) String nameOfAuthor) {

        return writerService.authorsByGenre(nameOfAuthor).stream()
                .map(Writer::getName).collect(Collectors.joining("\n"));
    }

    @ShellMethod(value = "Add comment to book. Args are: book title", key = {"add-comment-by-title", "комментарий-к-книге"})
    private String addCommentToBook(@Size(min = 1) String bookTitle, @Size(min = 1) String commentContent) {
        try {
            bookService.addCommentByBookTitle(bookTitle, commentContent);
            return getBooksTable(bookService.findAll());
        } catch (Exception e) {
            return e.getLocalizedMessage();
        }
    }


    private String getBooksTable(List<Book> books) {
        final String[][] data;
        final TableModel model;
        final TableBuilder tableBuilder;

        data = new String[books.size() + 1][5];
        model = new ArrayTableModel(data);
        tableBuilder = new TableBuilder(model);

        data[0][0] = "Код";
        data[0][1] = "Название книги";
        data[0][2] = "Жанры";
        data[0][3] = "Авторы";
        data[0][4] = "Комментарии";


        for (int i = 0; i < books.size(); i++) {
            int verticalIndex = i + 1;
            Book book = books.get(i);
            data[verticalIndex][0] = book.getId() + ")";
            data[verticalIndex][1] = book.getTitle();


            data[verticalIndex][2] =
                    //genreService.getGenresByBookId(book.getId()).stream().map(Genre::getName)
                    data[verticalIndex][2] = book.getGenres().stream().map(Genre::getName)
                    .collect(Collectors.joining("\n"));

            data[verticalIndex][3] = writerService.getAuthorsByBookId(book.getId()).stream()
                    .map(Writer::getName)
                    .collect(Collectors.joining("\n"));

            data[verticalIndex][4] = book.getComments().stream()
                    .map(Comment::getContent)
                    .collect(Collectors.joining("\n"));



            tableBuilder.on(at(i, 0)).addAligner(SimpleHorizontalAligner.values()[0]);
            tableBuilder.on(at(i, 1)).addAligner(SimpleVerticalAligner.values()[1]);

        }

        return tableBuilder.addFullBorder(BorderStyle.fancy_light).build().render(TERMINAL_WIDTH);
    }

}
