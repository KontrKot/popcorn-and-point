--liquibase formatted sql
--changeset nmakhalov:init-db

CREATE SCHEMA IF NOT EXISTS auth_service;

create table if not exists auth_service.users(
    id serial primary key,
    user_name varchar not null,
    password_hash varchar not null,
    first_name varchar not null,
    last_name varchar not null,
    email varchar not null
)