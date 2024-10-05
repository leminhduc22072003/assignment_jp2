package org.example.managementandstorebook.database;

import org.example.managementandstorebook.models.Book;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DatabaseConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/management_and_store_book";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    private static Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.out.println("Connect to database failed: " + e.getMessage());
        }
        return conn;
    }

    public static List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT id, title, author, release_date, content FROM Book";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String title = rs.getString("title");
                String author = rs.getString("author");
                LocalDate releaseDate = rs.getDate("release_date").toLocalDate();
                String content = rs.getString("content");

                Book book = new Book(id, title, author, releaseDate, content);
                books.add(book);
            }
        } catch (SQLException e) {
            System.out.println("Get list book failed: " + e.getMessage());
        }
        return books;
    }

    public static void addBook(Book book) {
        String sql = "INSERT INTO Book(title, author, release_date, content) VALUES(?, ?, ?, ?)";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, book.getTitle());
            pstmt.setString(2, book.getAuthor());
            pstmt.setDate(3, java.sql.Date.valueOf(book.getReleaseDate()));
            pstmt.setString(4, book.getContent());

            pstmt.executeUpdate();

            ResultSet generatedKeys = pstmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                book.setId(generatedKeys.getInt(1));
            }
        } catch (SQLException e) {
            System.out.println("Add new book failed: " + e.getMessage());
        }
    }

    public static void editBook(Book book) {
        String sql = "UPDATE Book SET title = ?, author = ?, release_date = ?, content = ? WHERE id = ?";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, book.getTitle());
            pstmt.setString(2, book.getAuthor());
            pstmt.setDate(3, java.sql.Date.valueOf(book.getReleaseDate()));
            pstmt.setString(4, book.getContent());
            pstmt.setInt(5, book.getId());

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Update success.");
            } else {
                System.out.println("No book found with the specified ID.");
            }
        } catch (SQLException e) {
            System.out.println("Edit book failed: " + e.getMessage());
        }
    }



    public static void deleteBook(int id) {
        String sql = "DELETE FROM Book WHERE id = ?";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            System.out.println("Delete success.");
        } catch (SQLException e) {
            System.out.println("Delete failed: " + e.getMessage());
        }
    }
}
