package com.wolox.training.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.google.common.base.Preconditions;
import com.wolox.training.commons.Constants;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Entity
@Data
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private long id;

    private String genre;

    @NotNull
    private String author;

    @NotNull
    private String image;

    @NotNull
    private String title;

    @NotNull
    private String subtitle;

    @NotNull
    private String publisher;

    @NotNull
    private String year;

    @NotNull
    private Integer pages;

    @Column(nullable = false, unique = true)
    private String isbn;

    @ManyToMany(mappedBy = "books", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<Users> users = new ArrayList<>();

    public void setAuthor(String author) {
        String message = String.format(Constants.CHECK_NULL_MESSAGE, "author");
        this.author = Preconditions.checkNotNull(author, message);
    }

    public void setImage(String image) {
        String message = String.format(Constants.CHECK_NULL_MESSAGE, "image");
        this.image = Preconditions.checkNotNull(image, message);
    }

    public void setTitle(String title) {
        String message = String.format(Constants.CHECK_NULL_MESSAGE, "title");
        this.title = Preconditions.checkNotNull(title, message);
    }

    public void setSubtitle(String subtitle) {
        String message = String.format(Constants.CHECK_NULL_MESSAGE, "subtitle");
        this.subtitle = Preconditions.checkNotNull(subtitle, message);
    }

    public void setPublisher(String publisher) {
        String message = String.format(Constants.CHECK_NULL_MESSAGE, "publisher");
        this.publisher = Preconditions.checkNotNull(publisher, message);
    }

    public void setYear(String year) {
        String message = String.format(Constants.CHECK_NULL_MESSAGE, "year");
        this.year = Preconditions.checkNotNull(year, message);
    }

    public void setPages(Integer pages) {
        String message = String.format(Constants.CHECK_NULL_MESSAGE, "pages");
        this.pages = Preconditions.checkNotNull(pages, message);
    }

    public void setIsbn(String isbn) {
        String message = String.format(Constants.CHECK_NULL_MESSAGE, "isbn");
        this.isbn = Preconditions.checkNotNull(isbn, message);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Book book = (Book) o;
        return isbn.equals(book.isbn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isbn);
    }
}
