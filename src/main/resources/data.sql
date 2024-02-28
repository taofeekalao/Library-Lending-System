INSERT INTO member (member_name, address, email) VALUES ('jonathan martin', 'Fife Park, KY169LY', 'user.one@st-andrews.ac.uk');
INSERT INTO member (member_name, address, email) VALUES ('adesola jacobs', 'the grange, KY169LY', 'user.two@st-andrews.ac.uk');
INSERT INTO member (member_name, address, email) VALUES ('jin tao', 'Gregory Place, KY169LY', 'user.three@st-andrews.ac.uk');
INSERT INTO member (member_name, address, email) VALUES ('abd hakeem', 'Powell, KY169LY', 'user.four@st-andrews.ac.uk');
INSERT INTO member (member_name, address, email) VALUES ('amir khan', 'David Russell Apartment, KY169LY', 'user.five@st-andrews.ac.uk');

INSERT INTO book (isbn, title, author, quantity_in_library, quantity_checked_out) VALUES ('9780743273565','The Great Gatsby', 'F. Scott Fitzgerald', 9, 2);
INSERT INTO book (isbn, title, author, quantity_in_library, quantity_checked_out) VALUES ('9780061120084','To Kill a Mockingbird', 'Harper Lee', 9, 6);
INSERT INTO book (isbn, title, author, quantity_in_library, quantity_checked_out) VALUES ('9780451524935','1984', 'George Orwell', 5, 5);
INSERT INTO book (isbn, title, author, quantity_in_library, quantity_checked_out) VALUES ('9780590353427','Harry Potter Series', 'J.K. Rowling', 8, 7);
INSERT INTO book (isbn, title, author, quantity_in_library, quantity_checked_out) VALUES ('9780241950425','The Catcher in the Rye', 'J.D. Salinger', 9, 2);

INSERT INTO checked_out_item (member, book, checkout_date, due_date, return_status) VALUES (00001, 00001, '2023-05-05', '2023-05-20', false);
INSERT INTO checked_out_item (member, book, checkout_date, due_date, return_status, return_date) VALUES (00002, 00002, '2023-06-10', '2023-06-15', false, '2023-06-14');
INSERT INTO checked_out_item (member, book, checkout_date, due_date, return_status) VALUES (00003, 00003, '2023-05-05', '2023-05-15', false);
INSERT INTO checked_out_item (member, book, checkout_date, due_date, return_status, return_date) VALUES (00004, 00004, '2023-10-05', '2023-10-20', false, '2023-10-08');
INSERT INTO checked_out_item (member, book, checkout_date, due_date, return_status, return_date) VALUES (00005, 00005, '2023-06-12', '2023-06-20', false, '2023-06-20');

INSERT INTO library (name) VALUES ('the main library');