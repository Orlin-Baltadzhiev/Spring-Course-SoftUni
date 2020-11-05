package com.softuni2.springintroex2.Services;

import com.softuni2.springintroex2.entities.Author;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;


public interface AuthorService {
    void seedAuthors() throws IOException;

    int getAllAuthorsCount();

    Author findAuthorById(Long id);

    List<Author> findAllAuthorsByCountOfBooks();
}
