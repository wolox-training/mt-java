package com.wolox.training.models;

import com.wolox.training.exceptions.BookAlreadyOwnedException;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Entity
@Data
@ApiModel(description = "User from the Wolox Training API")
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private long id;

    @NotNull
    @ApiModelProperty(notes = "The user's username")
    private String username;

    @NotNull
    @ApiModelProperty(notes = "The user's name")
    private String name;

    @NotNull
    @ApiModelProperty(notes = "The user's birthdate")
    private LocalDate birthdate;

    @ManyToMany(mappedBy = "users")
    private List<Book> books = new ArrayList<>();

    public List<Book> getBooks() {
        return (List<Book>) Collections.unmodifiableList(books);
    }

    public boolean addBook(Book book) {
        if (books.contains(book)) {
            throw new BookAlreadyOwnedException();
        }

        return books.add(book);
    }

    public boolean removeBook(Book book) {
        if (books.contains(book)) {
            return books.remove(book);
        }

        return false;
    }

}
