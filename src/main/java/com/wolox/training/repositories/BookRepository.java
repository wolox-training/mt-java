package com.wolox.training.repositories;

import com.wolox.training.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    public Book findByAuthor(String author);

}
