package com.wolox.training.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
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
    private String image = "-";

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

    @ManyToMany(cascade = {CascadeType.REFRESH, CascadeType.MERGE})
    @JsonBackReference
    private List<User> users = new ArrayList<>();

    public void setAuthor(String author) {
        String message = String.format(Constants.CHECK_NULL_MESSAGE, "author");
        Preconditions.checkArgument(!Strings.isNullOrEmpty(author), message);
        this.author = author;
    }

    public void setImage(String image) {
        String message = String.format(Constants.CHECK_NULL_MESSAGE, "image");
        Preconditions.checkArgument(!Strings.isNullOrEmpty(image), message);
        this.image = image;
    }

    public void setTitle(String title) {
        String message = String.format(Constants.CHECK_NULL_MESSAGE, "title");
        Preconditions.checkArgument(!Strings.isNullOrEmpty(title), message);
        this.title = title;
    }

    public void setSubtitle(String subtitle) {
        String message = String.format(Constants.CHECK_NULL_MESSAGE, "subtitle");
        Preconditions.checkArgument(!Strings.isNullOrEmpty(subtitle), message);
        this.subtitle = subtitle;
    }

    public void setPublisher(String publisher) {
        String message = String.format(Constants.CHECK_NULL_MESSAGE, "publisher");
        Preconditions.checkArgument(!Strings.isNullOrEmpty(publisher), message);
        this.publisher = publisher;
    }

    public void setYear(String year) {
        String message = String.format(Constants.CHECK_NULL_MESSAGE, "year");
        Preconditions.checkArgument(!Strings.isNullOrEmpty(year), message);
        this.year = year;
    }

    public void setPages(Integer pages) {
        String message = String.format(Constants.CHECK_NULL_MESSAGE, "pages");
        Preconditions.checkNotNull(pages, message);
        this.pages = pages;
    }

    public void setIsbn(String isbn) {
        String message = String.format(Constants.CHECK_NULL_MESSAGE, "isbn");
        Preconditions.checkArgument(!Strings.isNullOrEmpty(isbn), message);
        this.isbn = isbn;
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
