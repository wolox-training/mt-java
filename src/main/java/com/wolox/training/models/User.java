package com.wolox.training.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.wolox.training.commons.Constants;
import com.wolox.training.exceptions.BookAlreadyOwnedException;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
    @JsonIgnore
    @ApiModelProperty(notes = "The user's password")
    private String password;

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

    public void setUsername(String username) {
        String message = String.format(Constants.CHECK_NULL_MESSAGE, "username");
        Preconditions.checkArgument(!Strings.isNullOrEmpty(username), message);
        this.username = username;
    }

    public void setPassword(String password){
        String message = String.format(Constants.CHECK_NULL_MESSAGE, "password");
        Preconditions.checkArgument(!Strings.isNullOrEmpty(password), message);
        this.password = password;
    }

    public void setName(String name) {
        String message = String.format(Constants.CHECK_NULL_MESSAGE, "name");
        Preconditions.checkArgument(!Strings.isNullOrEmpty(name), message);
        this.name = name;
    }

    public void setBirthdate(LocalDate birthdate) {
        String message = String.format(Constants.CHECK_NULL_MESSAGE, "birthdate");
        this.birthdate = Preconditions.checkNotNull(birthdate, message);
    }

    public boolean addBook(Book book) {
        String message = String.format(Constants.CHECK_NULL_MESSAGE, "book");
        if (books.contains(Preconditions.checkNotNull(book, message))) {
            throw new BookAlreadyOwnedException();
        }

        return books.add(book);
    }

    public boolean removeBook(Book book) {
        String message = String.format(Constants.CHECK_NULL_MESSAGE, "book");
        if (books.contains(Preconditions.checkNotNull(book, message))) {
            return books.remove(book);
        }

        return false;
    }

}
