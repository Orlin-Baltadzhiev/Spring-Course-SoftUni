package com.softuni2.springintroex2.Services;

import com.softuni2.springintroex2.entities.Book;

import java.io.IOException;
import java.util.List;

public interface BookService  {
    void seedBooks() throws IOException;
    List<Book> getAllBooksAfter2000();
}
