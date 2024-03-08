INSERT INTO member (member_name, address, email_address)
VALUES ('jonathan martin', 'Fife Park, KY169LY', 'user.one@st-andrews.ac.uk');
INSERT INTO member (member_name, address, email_address)
VALUES ('adesola jacobs', 'the grange, KY169LY', 'user.two@st-andrews.ac.uk');
INSERT INTO member (member_name, address, email_address)
VALUES ('jin tao', 'Gregory Place, KY169LY', 'user.three@st-andrews.ac.uk');
INSERT INTO member (member_name, address, email_address)
VALUES ('abd hakeem', 'Powell, KY169LY', 'user.four@st-andrews.ac.uk');
INSERT INTO member (member_name, address, email_address)
VALUES ('amir khan', 'David Russell Apartment, KY169LY', 'user.five@st-andrews.ac.uk');
INSERT INTO member (member_name, address, email_address)
VALUES ('samanta mccarthy', 'Gregory Place Apartment, KY169LY', 'user.six@st-andrews.ac.uk');

INSERT INTO book (isbn, title, author, quantity_in_library, quantity_checked_out)
VALUES ('9780743273565', 'The Great Gatsby', 'F. Scott Fitzgerald', 7, 2);
INSERT INTO book (isbn, title, author, quantity_in_library, quantity_checked_out)
VALUES ('9780061120084', 'To Kill a Mockingbird', 'Harper Lee', 9, 1);
INSERT INTO book (isbn, title, author, quantity_in_library, quantity_checked_out)
VALUES ('9780451524935', '1984', 'George Orwell', 2, 1);
INSERT INTO book (isbn, title, author, quantity_in_library, quantity_checked_out)
VALUES ('9780590353427', 'Harry Potter Series', 'J.K. Rowling', 0, 1);
INSERT INTO book (isbn, title, author, quantity_in_library, quantity_checked_out)
VALUES ('9780241950425', 'The Catcher in the Rye', 'J.D. Salinger', 0, 2);

INSERT INTO checked_out_item (member, book, checkout_date, due_date, return_status)
VALUES (00001, 9780743273565, '2023-05-05', '2023-05-20', false);
INSERT INTO checked_out_item (member, book, checkout_date, due_date, return_status)
VALUES (00002, 9780061120084, '2023-05-05', '2023-05-20', false);
INSERT INTO checked_out_item (member, book, checkout_date, due_date, return_status)
VALUES (00003, 9780451524935, '2023-05-05', '2023-05-20', false);
INSERT INTO checked_out_item (member, book, checkout_date, due_date, return_status)
VALUES (00004, 9780590353427, '2023-05-05', '2023-05-20', false);
INSERT INTO checked_out_item (member, book, checkout_date, due_date, return_status)
VALUES (00005, 9780241950425, '2023-05-05', '2023-05-20', false);
INSERT INTO checked_out_item (member, book, checkout_date, due_date, return_status)
VALUES (00001, 9780241950425, '2023-05-05', '2023-05-20', false);
INSERT INTO checked_out_item (member, book, checkout_date, due_date, return_status)
VALUES (00002, 9780743273565, '2023-05-05', '2023-05-20', false);
INSERT INTO checked_out_item (member, book, checkout_date, due_date, return_status)
VALUES (00006, 9780743273565, '2023-05-05', '2023-05-20', true);

INSERT INTO library (name)
VALUES ('the main library');
