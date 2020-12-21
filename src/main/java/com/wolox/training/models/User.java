package com.wolox.training.models;

import com.wolox.training.exceptions.BookAlreadyOwnedException;
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

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    private String username;

    @NotNull
    private String name;

    @NotNull
    private LocalDate birthdate;

    @ManyToMany(mappedBy = "users")
    private List<Book> books = new ArrayList<>();

    public User() {

    }

    public long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public List<Book> getBooks() {
        return (List<Book>) Collections.unmodifiableList(books);
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public boolean addBook(Book book){
        if (books.contains(book)){
            throw new BookAlreadyOwnedException();
        }

        return books.add(book);
    }

    public boolean removeBook(Book book){

       if(books.contains(book)){
           return books.remove(book);
       }

       return false;
    }

}
