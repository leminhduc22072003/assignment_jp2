package org.example.managementandstorebook.models;

import java.time.LocalDate;

public class Book {
    private int id;
    private String title;
    private String author;
    private LocalDate releaseDate;

    private String content;

    public Book(int id, String title, String author, LocalDate releaseDate, String content) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.releaseDate = releaseDate;
        this.content = content;
    }

    public Book(String title, String author, LocalDate releaseDate, String content) {
        this.title = title;
        this.author = author;
        this.releaseDate = releaseDate;
        this.content = content;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}