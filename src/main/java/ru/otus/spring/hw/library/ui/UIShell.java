package ru.otus.spring.hw.library.ui;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.table.*;
import org.springframework.stereotype.Service;
import ru.otus.spring.hw.library.domain.Book;
import ru.otus.spring.hw.library.domain.Genre;
import ru.otus.spring.hw.library.domain.Writer;
import ru.otus.spring.hw.library.service.BookService;
import ru.otus.spring.hw.library.service.GenreService;
import ru.otus.spring.hw.library.service.WriterService;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.shell.table.CellMatchers.at;

@Service
@ShellComponent
public class UIShell {


    private final BookService bookService;
    private final GenreService genreService;
    private final WriterService writerService;

    public UIShell(BookService bookService, GenreService genreService, WriterService writerService) {
        this.bookService = bookService;
        this.genreService = genreService;
        this.writerService = writerService;
    }


    @ShellMethod(value = "Show all book", key = {"show-books", "все-книги"})
    private String showBooks() {
        return getBooksTable(bookService.findAll());
    }

    private String getBooksTable(List<Book> books) {
        final String[][] data;
        final TableModel model;
        final TableBuilder tableBuilder;

        data = new String[books.size() + 1][4];
        model = new ArrayTableModel(data);
        tableBuilder = new TableBuilder(model);

        data[0][0] = "№";
        data[0][1] = "Название книги";
        data[0][2] = "Жанры";
        data[0][3] = "Авторы";

/*
            tableBuilder.on(at(0, 0)).addAligner(SimpleHorizontalAligner.values()[0]);
            tableBuilder.on(at(0, 1)).addAligner(SimpleVerticalAligner.values()[1]);
            tableBuilder.on(at(0, 1)).addAligner(SimpleVerticalAligner.values()[1]);
            tableBuilder.on(at(0, 1)).addAligner(SimpleVerticalAligner.values()[1]);
*/
        for (int i = 0; i < books.size(); i++) {
            int verticalIndex = i + 1;
            data[verticalIndex][0] = i + ")";
            data[verticalIndex][1] = books.get(i).getTitle();

            data[verticalIndex][2] = books.get(i).getGenres()
                    .stream().map(Genre::getName)
                    .collect(Collectors.joining());

            data[verticalIndex][3] = books.get(i).getAuthors().stream()
                    .map(Writer::getName)
                    .collect(Collectors.joining());


            tableBuilder.on(at(i, 0)).addAligner(SimpleHorizontalAligner.values()[0]);
            tableBuilder.on(at(i, 1)).addAligner(SimpleVerticalAligner.values()[1]);

        }

        return tableBuilder.addFullBorder(BorderStyle.fancy_light).build().render(80);
    }

            /*
        private Availability finishAvailability() {
            return exam != null
                    ? Availability.available()
                    : Availability.unavailable("you should start the exam before finish!");
        }*/


}
