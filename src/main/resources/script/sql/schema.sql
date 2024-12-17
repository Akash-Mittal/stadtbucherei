-- Create the Author table
CREATE TABLE Author (
    author_id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    date_of_birth DATE NOT NULL
);

-- Create the Book table
CREATE TABLE Book (
    book_id SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    genre VARCHAR(100),
    price DECIMAL(10, 2),
    author_id INT NOT NULL,
    CONSTRAINT fk_author FOREIGN KEY (author_id) REFERENCES Author(author_id)
);

-- Create the Member table
CREATE TABLE Member (
    member_id SERIAL PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    address TEXT,
    phone_number VARCHAR(20)
);

-- Create the Loan table
CREATE TABLE Loan (
    loan_id SERIAL PRIMARY KEY,
    member_id INT NOT NULL,
    book_id INT NOT NULL,
    lend_date DATE NOT NULL,
    return_date DATE NOT NULL,
    CONSTRAINT fk_member FOREIGN KEY (member_id) REFERENCES Member(member_id),
    CONSTRAINT fk_book FOREIGN KEY (book_id) REFERENCES Book(book_id)
);
