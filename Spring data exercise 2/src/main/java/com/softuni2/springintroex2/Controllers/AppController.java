package com.softuni2.springintroex2.Controllers;

import com.softuni2.springintroex2.Services.AuthorService;
import com.softuni2.springintroex2.Services.BookService;
import com.softuni2.springintroex2.Services.CategoryService;
import com.softuni2.springintroex2.entities.Book;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class AppController implements CommandLineRunner {

    private final CategoryService categoryService;
    private final AuthorService authorService;
    private final BookService bookService;

    public AppController(CategoryService categoryService, AuthorService authorService, BookService bookService) {
        this.categoryService = categoryService;
        this.authorService = authorService;

        this.bookService = bookService;
    }

    @Override
    public void run(String... args) throws Exception {
    this.categoryService.seedCategories();
    this.authorService.seedAuthors();
    this.bookService.seedBooks();
     //ex 1
        //List<Book>  books = this.bookService.getAllBooksAfter2000();

     //Ex 3
        this.authorService
                .findAllAuthorsByCountOfBooks()
                .forEach(a -> {
                    System.out.printf("%s %s %d%n",
                            a.getFirstName(),a.getLastName(),a.getBooks().size());
                });
        System.out.println();
    }
}
