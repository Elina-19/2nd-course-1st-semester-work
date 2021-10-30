create table account(
    id serial primary key,
    nickname varchar(30),
    email varchar(100),
    password varchar(50),
    image_path varchar(50),
    uuid varchar(50)
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

insert into book(name, account_id, path_to_content, image_path, date_add, description, rate, number_of_rates, number_of_comments, number_of_reviews)
values (:name, :accountId, :pathToContent, :imagePath, now(), :description, :rate, :numberOfRates, :numberOfComments, :numberOfReviews);

select id, name, account_id, path_to_content, image_path, date_add, description, rate, number_of_rates, number_of_comments, number_of_reviews from book
    where book.id = :id;


update account set uuid = :uuid where account.email = :email;

drop table account;