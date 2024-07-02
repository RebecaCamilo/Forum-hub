create table topicos (
    id bigserial not null primary key,
    titulo varchar(100) not null,
    mensagem varchar(500) not null,
    data_criacao timestamp not null,
    status smallint not null default 1,
    autor varchar(100) not null,
    curso varchar(100) not null
);
