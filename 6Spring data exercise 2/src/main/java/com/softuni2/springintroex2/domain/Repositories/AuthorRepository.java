package com.softuni2.springintroex2.domain.Repositories;

import com.softuni2.springintroex2.domain.entities.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

    @Query("SELECT a FROM  Author AS a ORDER BY a.books.size DESC ")
    List<Author> findAuthorByCountOfBook();
//  @Query("SELECT DISTINCT a FROM Author As a JOIN a.books As bo where bo.releaseDate < '1990-01-01' ")
    @Query("SELECT DISTINCT  a FROM Author AS a JOIN a.books AS b  WHERE  b.releaseDate < '1990-01-01'")
    List<Author> booksBefore1990();

    Set<Author> findAllByFirstNameEndingWith(String end);
}
