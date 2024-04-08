create table course (
    id bigint generated by default as identity,
    title varchar(255) not null,
    creator_id bigint not null,
    created_at timestamp not null,
    updated_at timestamp,
    primary key (id)
);

create table ns_user (
    id bigint generated by default as identity,
    user_id varchar(20) not null,
    password varchar(20) not null,
    name varchar(20) not null,
    email varchar(50),
    created_at timestamp not null,
    updated_at timestamp,
    primary key (id)
);

create table question (
    id bigint generated by default as identity,
    created_at timestamp not null,
    updated_at timestamp,
    contents clob,
    deleted boolean not null,
    title varchar(100) not null,
    writer_id bigint,
    primary key (id)
);

create table answer (
    id bigint generated by default as identity,
    created_at timestamp not null,
    updated_at timestamp,
    contents clob,
    deleted boolean not null,
    question_id bigint,
    writer_id bigint,
    primary key (id)
);

create table delete_history (
    id bigint not null,
    content_id bigint,
    content_type varchar(255),
    created_date timestamp,
    deleted_by_id bigint,
    primary key (id)
);

CREATE TABLE cover_image (
     id BIGINT AUTO_INCREMENT PRIMARY KEY,
     session_id BIGINT NOT NULL,
     width INT NOT NULL,
     height INT NOT NULL,
     file_size BIGINT NOT NULL,
     file_name VARCHAR(100) NOT NULL,
     extension VARCHAR(100) NOT NULL,
     file_path VARCHAR(100) NOT NULL
);

CREATE TABLE session_listener(
    session_id BIGINT NOT NULL,
    ns_user_id BIGINT NOT NULL
);

CREATE TABLE class_session (
   id BIGINT AUTO_INCREMENT PRIMARY KEY,
   course_id BIGINT NOT NULL,
   title VARCHAR(100) NOT NULL,
   state VARCHAR(20) NOT NULL,
   recruitment VARCHAR(20) NOT NULL,
   capacity INT NOT NULL,
   amount BIGINT NOT NULL,
   selection VARCHAR(20) NOT NULL,
   start_date timestamp NOT NULL,
   end_date timestamp NOT NULL,
   created_at timestamp NOT NULL,
   updated_at timestamp
);


CREATE TABLE enrollment (
   id BIGINT AUTO_INCREMENT PRIMARY KEY,
   session_id BIGINT NOT NULL,
   ns_user_id BIGINT NOT NULL,
   state VARCHAR(20) NOT NULL,
   created_at timestamp NOT NULL,
   updated_at timestamp
);
