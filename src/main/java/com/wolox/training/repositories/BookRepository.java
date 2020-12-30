package com.wolox.training.repositories;

import com.wolox.training.models.Book;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    public Optional<Book> findByAuthor(String author);

    public Optional<Book> findByIsbn(String isbn);

    @Query("SELECT b FROM Book b WHERE (:publisherName IS NULL OR b.publisher = :publisherName) "
            + "and (:genre IS NULL OR b.genre = :genre) "
            + "and (:year IS NULL OR b.year = :year)")
    public Page<Book> findByPublisherAndGenreAndYear(@Param("publisherName") String publisher,
            @Param("genre") String genre, @Param("year") String year, Pageable pageable);

    @Query("SELECT b FROM Book b WHERE (:publisherName = '' OR b.publisher = :publisherName) "
            + "and (:genre = '' OR b.genre = :genre) "
            + "and (:year = '' OR b.year = :year)"
            + "and (:author = '' OR b.author = :author)"
            + "and (:image = '' OR b.image = :image)"
            + "and (:title = '' OR b.title = :title)"
            + "and (:subtitle = '' OR b.subtitle = :subtitle)"
            + "and (:pages = 0 OR b.pages = :pages)"
            + "and (:isbn = '' OR b.isbn = :isbn)")
    public Page<Book> findAllBooks(@Param("publisherName") String publisher,
            @Param("genre") String genre, @Param("year") String year,
            @Param("author") String author,
            @Param("image") String image, @Param("title") String title,
            @Param("subtitle") String subtitle,
            @Param("pages") Integer pages, @Param("isbn") String isbn,
            Pageable pageable);

}
