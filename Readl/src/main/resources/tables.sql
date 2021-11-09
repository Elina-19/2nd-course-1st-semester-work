create table account(
    id serial primary key,
    nickname varchar(30),
    email varchar(100),
    password varchar(50),
    image_path varchar(50),
    uuid varchar(50),
    token varchar(50)
);

create table book(
    id serial primary key,
    name varchar(50),
    account_id int,
    foreign key (account_id) references account(id),
    path_to_content varchar(50),
    image_path varchar(50),
    date_add timestamp,
    description text,
    rate double precision,
    number_of_rates integer,
    number_of_comments integer,
    number_of_reviews integer
);

create table chapter(
    id serial primary key,
    name varchar(50),
    book_id int,
    foreign key (book_id) references book(id),
    content_path varchar(50),
    mime_type varchar(20)
);

create table genre(
    id serial primary key,
    name varchar(20)
);

insert into genre(name) values ('классика');

create table book_genre(
    book_id int,
    foreign key (book_id) references book(id),
    genre_id int,
    foreign key (genre_id) references genre(id)
);

create table favourite(
    account_id int,
    foreign key (account_id) references account(id),
    book_id int,
    foreign key (book_id) references book(id),
    status varchar(20)
);

select g.id, g.name from genre g
inner join book_genre bg on g.id = bg.genre_id
inner join book b on bg.book_id = b.id;

select id, name, book_id, content_path, mime_type from chapter where chapter.book_id = :bookId;

insert into book(name, account_id, path_to_content, image_path, date_add, description, rate, number_of_rates, number_of_comments, number_of_reviews)
values (:name, :accountId, :pathToContent, :imagePath, now(), :description, :rate, :numberOfRates, :numberOfComments, :numberOfReviews);

select id, name, account_id, path_to_content, image_path, date_add, description, rate, number_of_rates, number_of_comments, number_of_reviews from book
    where book.id = :id;


update account set uuid = :uuid where account.email = :email;

create table review(
    id serial primary key,
    date timestamp,
    account_id int,
    foreign key (account_id) references account(id),
    book_id int,
    foreign key (book_id) references book(id),
    content text
);

insert into review(date, account_id, book_id, content) values (now(), :accountId, :bookId, :content);
drop table account;