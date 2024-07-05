create table usuarios(
    id bigserial not null primary key,
    login varchar(100) not null,
    senha varchar(255) not null
);
