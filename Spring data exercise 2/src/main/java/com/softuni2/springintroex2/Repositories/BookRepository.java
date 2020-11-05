package com.softuni2.springintroex2.Repositories;

import com.softuni2.springintroex2.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;


@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
        List<Book> findAllByReleaseDateAfter(LocalDate localDate);
}
