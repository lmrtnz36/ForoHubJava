create table topics(
    id bigint not null auto_increment,
    title varchar(100) not null unique,
    message varchar(300) not null unique,
    date datetime not null,
    status boolean not null,
    author_id bigint not null,
    course varchar(100) not null,

    primary key (id)

);