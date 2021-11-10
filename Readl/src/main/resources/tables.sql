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

create table review(
    id serial primary key,
    date timestamp,
    account_id int,
    foreign key (account_id) references account(id),
    book_id int,
    foreign key (book_id) references book(id),
    content text
);