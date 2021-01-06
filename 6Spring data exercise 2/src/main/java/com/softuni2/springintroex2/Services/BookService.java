package com.softuni2.springintroex2.Services;

import com.softuni2.springintroex2.Services.models.BookInfo;
import com.softuni2.springintroex2.domain.entities.Book;

import java.io.IOException;
import java.util.List;

public interface BookService  {
    void seedBooks() throws IOException;
    List<Book> getAllBooksAfter2000();

    List<Book>getAllBooksBefore1990();

    List<Book>findAllBooksWithAuthorGeorgePowell();

    void printAllBooksByAgeRestriction(String ageRes);

    void printAllBooksByEditionTypeAndCopies();

    void printAllBooksByPriceInBounds();

    void printAllBooksNotInYear(String year);

    void printAllBooksBeforeDate(String date);

    void printAllBooksWithAuthorLastNameStarting(String start);

    void printNumberBooksCountWithTitleLengthBigger( int length);

    BookInfo findBookByTitle(String title);

    void printUpdatedCopiesCount(String date, int copies);


}
