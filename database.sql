CREATE DATABASE management_and_store_book;

USE management_and_store_book;

CREATE TABLE Book (
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    title TEXT NOT NULL,
    author TEXT NOT NULL,
    release_date DATE NOT NULL,
    content TEXT
);

INSERT INTO Book (title, author, release_date, content) VALUES
('The Great Gatsby', 'F. Scott Fitzgerald', '1925-04-10', 'A novel about the American dream and the disillusionment that comes with it.'),
('To Kill a Mockingbird', 'Harper Lee', '1960-07-11', 'A novel about the serious issues of rape and racial inequality.'),
('1984', 'George Orwell', '1979-06-08', 'A dystopian novel set in a totalitarian society ruled by Big Brother.'),
('Pride and Prejudice', 'Jane Austen', '1913-01-28', 'A romantic novel that critiques the British landed gentry at the end of the 18th century.'),
('The Catcher in the Rye', 'J.D. Salinger', '1951-07-16', 'A novel that explores the themes of teenage angst and alienation.'),
('The Hobbit', 'J.R.R. Tolkien', '1937-09-21', 'A fantasy novel about the adventures of Bilbo Baggins, a hobbit.'),
('Moby-Dick', 'Herman Melville', '1951-10-18', 'A novel about the obsessive quest of Ahab for revenge on Moby Dick, the giant whale.'),
('War and Peace', 'Leo Tolstoy', '1969-01-01', 'A historical novel that chronicles the French invasion of Russia.'),
('The Odyssey', 'Homer', '1979-01-10', 'An epic poem that tells the story of Odysseus’s journey home after the Trojan War.'),
('The Alchemist', 'Paulo Coelho', '1988-01-01', 'A philosophical book that follows a shepherd named Santiago on his journey to find treasure.');
