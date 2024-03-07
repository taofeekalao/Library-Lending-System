/**
  Library Management System Database Schema(h2)
 */
DROP TABLE IF EXISTS member;
DROP TABLE IF EXISTS book;
DROP TABLE IF EXISTS checked_out_item;
DROP TABLE IF EXISTS library;


/**
  Create the Member table
 */
CREATE TABLE member
(
    member_id     LONG PRIMARY KEY AUTO_INCREMENT NOT NULL,
    member_name   VARCHAR(255)                    NOT NULL,
    address       VARCHAR(255),
    email_address VARCHAR(255) UNIQUE             NOT NULL
);

/**
  Create the Book table
 */
CREATE TABLE book
(
    book_id              INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    isbn                 VARCHAR(20) UNIQUE             NOT NULL,
    title                VARCHAR(255)                   NOT NULL,
    author               VARCHAR(100)                   NOT NULL,
    quantity_in_library  INT                            NOT NULL,
    quantity_checked_out INT DEFAULT 0                  NOT NULL
);


/**
  Create the Checked Out Record table
 */
CREATE TABLE checked_out_item
(
    checked_out_book_item_id INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    member                   INT                            NOT NULL,
    book                     LONG                           NOT NULL,
    checkout_date            DATE                           NOT NULL,
    due_date                 DATE                           NOT NULL,
    return_status            BOOLEAN DEFAULT FALSE          NOT NULL,
    return_date              DATE
);

/**
  Create the Library table
 */
CREATE TABLE library
(
    id   INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    name VARCHAR(50)                    NOT NULL
);
