package com.softuni2.springintroex2.Services;

import com.softuni2.springintroex2.domain.entities.Author;

import java.io.IOException;
import java.util.List;


public interface AuthorService {
    void seedAuthors() throws IOException;

    int getAllAuthorsCount();

    Author findAuthorById(Long id);

    List<Author> findAllAuthorsByCountOfBooks();

    List<Author> findAllAuthorsWithBooksBefore();

    void printAllAuthorEndWith(String end);

    void printAllAuthorsByBookCopies();



}
