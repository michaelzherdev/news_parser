DROP DATABASE IF EXISTS news_parser;
CREATE DATABASE news_parser;
\c news_parser;

DROP TABLE IF EXISTS news;
DROP SEQUENCE IF EXISTS global_seq;

CREATE SEQUENCE global_seq START 1;

CREATE TABLE news
(
  id         INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
  title       VARCHAR NOT NULL,
  text      VARCHAR NOT NULL,
  category VARCHAR NOT NULL,
  image   VARCHAR NOT NULL,
  url   VARCHAR NOT NULL,
  date TIMESTAMP           DEFAULT now()
);