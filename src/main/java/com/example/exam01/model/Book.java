package com.example.exam01.model;

import jakarta.persistence.*;
import java.util.Date;

@Entity
public class Book extends Document {
    private String author;
    private String isbn;

    @Temporal(TemporalType.DATE)
    private Date datePublication;

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }

    public Date getDatePublication() { return datePublication; }
    public void setDatePublication(Date datePublication) { this.datePublication = datePublication; }
}