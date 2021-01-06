package com.softuni2.springintroex2.Services.Impl;

import com.softuni2.springintroex2.Services.models.BookInfo;
import com.softuni2.springintroex2.domain.Repositories.BookRepository;
import com.softuni2.springintroex2.Services.AuthorService;
import com.softuni2.springintroex2.Services.BookService;
import com.softuni2.springintroex2.Services.CategoryService;
import com.softuni2.springintroex2.Utils.FileUtil;
import com.softuni2.springintroex2.domain.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static com.softuni2.springintroex2.Constants.GlobalConstants.*;

@Service
@Transactional
public class BookServiceImpl implements BookService {
    private final CategoryService categoryService;
    private final BookRepository bookRepository;
    private final AuthorService authorService;
    private final FileUtil fileUtil;
    @Autowired
    public BookServiceImpl(CategoryService categoryService, BookRepository bookRepository, AuthorService authorService, FileUtil fileUtil) {
        this.categoryService = categoryService;
        this.bookRepository = bookRepository;
        this.authorService = authorService;
        this.fileUtil = fileUtil;
    }

    @Override
    public void seedBooks() throws IOException {

        if(this.bookRepository.count()!= 0 ){
            return;
        }

        String[] fileContent = this.fileUtil
                .readFileContent(BOOKS_FILE_PATH);

        Arrays.stream(fileContent)
                .forEach(r-> {
                    String[] params = r.split("\\s+");

                    Author author = this.getRandomAuthor();

                    EditionType editionType = EditionType.values()
                            [Integer.parseInt(params[0])];

                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
                    LocalDate releaseDate= LocalDate.parse(params[1], formatter);

                    int copies = Integer.parseInt(params[2]);

                    BigDecimal price = new BigDecimal(params[3]);

                    AgeRestriction ageRestriction = AgeRestriction.values()
                            [Integer.parseInt(params[4])];

                    String title = this.getTitle(params);

                    Set<Category> categories = this.getRandomCategories();

                    Book book = new Book();

                    book.setAuthor(author);
                    book.setEditionType(editionType);
                    book.setReleaseDate(releaseDate);
                    book.setCopies(copies);
                    book.setPrice(price);
                    book.setAgeRestriction(ageRestriction);
                    book.setTitle(title);
                    book.setCategories(categories);

                    this.bookRepository.saveAndFlush(book);
                });


    }

    @Override
    public void printAllBooksByAgeRestriction(String ageRes) {
        AgeRestriction ageRestriction = AgeRestriction.valueOf(ageRes.toUpperCase());

        this.bookRepository.findAllByAgeRestriction(ageRestriction)
                .forEach(b -> System.out.println(b.getTitle()));
    }

    @Override
    public void printAllBooksByEditionTypeAndCopies() {
        this.bookRepository.findAllByEditionTypeAndCopiesIsLessThan(EditionType.GOLD, 5000)
                .forEach(b -> System.out.println(b.getTitle()));
    }

    @Override
    public void printAllBooksByPriceInBounds() {
        this.bookRepository.findAllByPriceLessThanOrPriceGreaterThan(BigDecimal.valueOf(5),BigDecimal.valueOf(40))
                .forEach(b-> System.out.printf("%s - $%s%n",b.getTitle(), b.getPrice()));
    }

    @Override
    public void printAllBooksNotInYear(String year) {
        this.bookRepository.findAllBooksNotInYear(year)
                .forEach(b -> System.out.println(b.getTitle()));
    }

    @Override
    public void printAllBooksBeforeDate(String date) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate localDate = LocalDate.parse(date,dateTimeFormatter);

        this.bookRepository.findAllByReleaseDateIsLessThan(localDate)
                .forEach(b-> System.out.printf("%s %s %s%n",
                        b.getTitle(),b.getEditionType(),b.getPrice()));
    }

    @Override
    public void printAllBooksWithAuthorLastNameStarting(String start) {
        this.bookRepository.findAllByAuthorLastNameStartingWith(start)
                .forEach(b-> System.out.printf("%s (%s %s)%n", b.getTitle(),b.getAuthor().getFirstName(),b.getAuthor().getLastName()));
    }

    @Override
    public void printNumberBooksCountWithTitleLengthBigger(int length) {
        System.out.println(this.bookRepository.getNumberOfBooksWithTitleLength(length));
    }

    @Override
    public BookInfo findBookByTitle(String title) {
        Book book = this.bookRepository.findByTitle(title);
        BookInfo bookInfo = new BookInfo(book.getTitle(), book.getPrice(), book.getCopies());
        return bookInfo;
    }

    @Override
    public void printUpdatedCopiesCount(String date, int copies) {
        DateTimeFormatter dfs=DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate localDate = LocalDate.parse(date, dfs);


        int updatedRows = this.bookRepository.updateCopies(copies, localDate);
        System.out.println(updatedRows* copies);
    }

    @Override
    public List<Book> getAllBooksAfter2000() {
        DateTimeFormatter  formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
        LocalDate releaseDate = LocalDate.parse("31/12/2000", formatter);


        List<Book> allByReleaseDate = this.bookRepository.findAllByReleaseDateAfter(releaseDate);
        return allByReleaseDate;
    }

    @Override
    public List<Book> getAllBooksBefore1990() {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
    LocalDate releaseDate = LocalDate.parse("1/1/1990");

    List<Book>allByReleaseDateBefore = this.bookRepository.findAllByReleaseDateBefore(releaseDate);

    return allByReleaseDateBefore;
    }

    @Override
    public List<Book> findAllBooksWithAuthorGeorgePowell() {
        return this.bookRepository.booksWithAuthorGeorgePowell();
    }

    private Set<Category> getRandomCategories() {
        Random random = new Random();
        Set<Category> result = new HashSet<>();
        int bound = random.nextInt(7)+1;

        for (int i = 1; i <=bound ; i++) {
            result.add(this.categoryService.getCategoryById((long) i));
        }
        return result;
    }

    private String getTitle(String[] params) {
        StringBuilder sb = new StringBuilder();

        for (int i = 5; i <params.length ; i++) {
            sb.append(params[i])
                    .append(" ");

        }
        return sb.toString().trim();
    }

    private Author getRandomAuthor() {
        Random random = new Random();
        int randomId = random.nextInt(this.authorService.getAllAuthorsCount()) + 1;

        return this.authorService.findAuthorById((long) randomId);
    }
}

















