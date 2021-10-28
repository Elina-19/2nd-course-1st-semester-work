create table account(
    id serial primary key,
    nickname varchar(30),
    email varchar(100),
    password varchar(50),
    image_path varchar(50),
    uuid varchar(50)
);

update account set uuid = :uuid where account.email = :email;

drop table account;