
-- Add sample data for Author
INSERT INTO Author (name, date_of_birth)
VALUES ('J.K. Rowling', '1965-07-31'),
       ('George R.R. Martin', '1948-09-20');

-- Add sample data for Book
INSERT INTO Book (title, genre, price, author_id)
VALUES ('Harry Potter and the Sorcerer\'s Stone', 'Fantasy', 19.99, 1),
       ('A Game of Thrones', 'Fantasy', 29.99, 2);

-- Add sample data for Member
INSERT INTO Member (username, email, address, phone_number)
VALUES ('john_doe', 'john@example.com', '123 Main St, Cityville', '123-456-7890'),
       ('jane_smith', 'jane@example.com', '456 Oak St, Townsville', '987-654-3210');

-- Add sample data for Loan
INSERT INTO Loan (member_id, book_id, lend_date, return_date)
VALUES (1, 1, '2024-12-01', '2024-12-15'),
       (2, 2, '2024-12-01', '2024-12-15');
