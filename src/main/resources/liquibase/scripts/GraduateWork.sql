--liquibase formatted sql

--changeset KorolchukVladislav:1
create table image
(
    id         bigserial primary key,
    file_size  bigint,
    media_type varchar(255),
    data       bytea
);

create table users
(
    id              bigint primary key,
    first_name      varchar(255),
    last_name       varchar(255),
    email           varchar(255),
    currentPassword varchar(255),
    phone           varchar(255),
    city            varchar(255),
    reg_date        varchar(255),
    image           bigint references image (id),
    role            varchar(255)
);

create table ads
(
    id bigint primary key,
    title varchar(255),
    description varchar(255),
    price int,
    author bigint references users(id),
    image bigint references image(id)
    );

create table comment
(
    id bigserial primary key,
    created_at varchar(255),
    text varchar(255),
    author bigint references users(id),
    ad bigint references ads(id)
);

