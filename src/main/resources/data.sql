insert into WRITERS (id, `name`) values (1, 'Dan Abnett');


insert into BOOKS (id, `title`) values (1, 'PARIA');
insert into BOOKS (id, `title`) values (2, 'Hobbit');
insert into BOOKS (id, `title`) values (3, 'Codex of Alera');

insert into GENRES (id, `name`) values (1, 'Warhammer 40000');

insert into BOOK_GENRE (id, bookid, genreid) values (1, 1, 1);
insert into BOOK_AUTHOR (id, bookid, authorid) values (1, 1, 1);

