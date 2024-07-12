create table responses(
    id bigint not null auto_increment,
    message varchar(300) not null,
    date datetime not null,
    solution boolean not null,
    topic_id bigint not null,
    author_id bigint not null,

    primary key (id)
);