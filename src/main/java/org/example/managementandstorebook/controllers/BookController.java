package org.example.managementandstorebook.controllers;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import org.example.managementandstorebook.database.DatabaseConnection;
import org.example.managementandstorebook.models.Book;
import javafx.collections.ObservableList;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class BookController {

    @FXML
    private TextField txtId, txtTitle, txtAuthor;

    @FXML
    private DatePicker dateReleaseDate;

    @FXML
    private TextArea txtContent;

    @FXML
    private TableView<Book> tableBooks;

    @FXML
    private TableColumn<Book, Integer> colId;

    @FXML
    private TableColumn<Book, String> colTitle, colAuthor;

    @FXML
    private TableColumn<Book, LocalDate> colReleaseDate;

    @FXML
    private TableColumn<Book, Void> colActions;

    private ObservableList<Book> bookList;

    @FXML
    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colAuthor.setCellValueFactory(new PropertyValueFactory<>("author"));
        colReleaseDate.setCellValueFactory(new PropertyValueFactory<>("releaseDate"));

        colActions.setCellFactory(column -> new TableCell<Book, Void>() {
            private final Button editButton = new Button("Edit");
            private final Button deleteButton = new Button("Delete");

            @Override
            public void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || getTableRow() == null || getTableRow().getItem() == null) {
                    setGraphic(null);
                } else {
                    Book book = getTableRow().getItem();
                    editButton.setOnAction(e -> editBook(book));
                    deleteButton.setOnAction(e -> deleteBook(book));
                    HBox hBox = new HBox(5, editButton, deleteButton);
                    setGraphic(hBox);
                    setAlignment(Pos.CENTER);
                }
            }
        });

        bookList = FXCollections.observableArrayList();
        loadBooksFromDatabase();
    }


    @FXML
    private void saveBook() {
        Integer id = null;
        if (!txtId.getText().isEmpty()) {
            id = Integer.parseInt(txtId.getText());
        }
        
        String title = txtTitle.getText();
        String author = txtAuthor.getText();
        LocalDate releaseDate = dateReleaseDate.getValue();
        String content = txtContent.getText();

        if (title.isEmpty() || author.isEmpty() || releaseDate == null || content.isEmpty()) {
            showAlert("Please enter all required information.", "Notification", Alert.AlertType.WARNING);
            return;
        }

        if (id == null) {
            Book newBook = new Book(title, author, releaseDate, content);
            addBook(newBook);

        } else {
            Book editBook = new Book(id, title, author, releaseDate, content);
            DatabaseConnection.editBook(editBook);
            tableBooks.refresh();
        }
        clearForm();
    }


    @FXML
    private void clearForm() {
        txtId.clear();
        txtTitle.clear();
        txtAuthor.clear();
        dateReleaseDate.setValue(null);
        txtContent.clear();
    }


    @FXML
    public void addBook(Book book) {
        DatabaseConnection.addBook(book);
        bookList.add(book);
    }


    @FXML
    private void exportBooksToTxt() {
        List<Book> books = DatabaseConnection.getAllBooks();

        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        String fileName = "books_" + timestamp + ".txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (Book book : books) {
                writer.write("ID: " + book.getId() + "\n");
                writer.write("Title: " + book.getTitle() + "\n");
                writer.write("Author: " + book.getAuthor() + "\n");
                writer.write("Release Date: " + book.getReleaseDate() + "\n");
                writer.write("Content: " + book.getContent() + "\n");
                writer.write("----------------------------------------\n");
            }
            System.out.println("Export success. File: " + fileName);
        } catch (IOException e) {
            System.out.println("Export failed: " + e.getMessage());
        }
    }

    private void loadBooksFromDatabase() {
        List<Book> books = DatabaseConnection.getAllBooks();
        bookList.addAll(books);
        tableBooks.setItems(bookList);
    }

    private void showAlert(String message, String title, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void editBook(Book book) {
        txtId.setText(String.valueOf(book.getId()));
        txtTitle.setText(book.getTitle());
        txtAuthor.setText(book.getAuthor());
        dateReleaseDate.setValue(book.getReleaseDate());
        txtContent.setText(book.getContent());
    }

    private void deleteBook(Book book) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete book");
        alert.setHeaderText("Do you want to delete this book?");
        alert.setContentText("Name: " + book.getTitle());

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                DatabaseConnection.deleteBook(book.getId());
                bookList.remove(book);
            }
        });
    }

}
