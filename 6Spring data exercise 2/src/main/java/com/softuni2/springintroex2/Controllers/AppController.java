package com.softuni2.springintroex2.Controllers;

import com.softuni2.springintroex2.Services.AuthorService;
import com.softuni2.springintroex2.Services.BookService;
import com.softuni2.springintroex2.Services.CategoryService;
import com.softuni2.springintroex2.Services.models.BookInfo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;

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
        // 

//        System.out.println("Choose from 1 to 4 to check my exercise, please be nice with me :)");
//        System.out.printf("Chose 0 for exit\r\n");
//        System.out.print("Enter your numbere here: ");
//
//
//
//

//
//        String input = reader.readLine();
//
//       while (!"0".equals(input)) {
//           switch (input) {
//               case "1":
//                   this.bookService
//                           .getAllBooksAfter2000()
//                           .forEach(b -> {
//                               System.out.printf("%s%n", b.getTitle());
//                           });
//                   break;
//               case "2":
//                   this.authorService
//                           .findAllAuthorsWithBooksBefore()
//                           .forEach(a -> {
//                               System.out.printf("%s %s%n", a.getFirstName(), a.getLastName());
//                           });
//                   break;
//               case "3":
//                   this.authorService
//                           .findAllAuthorsByCountOfBooks()
//                           .forEach(a -> {
//                               System.out.printf("%s %s %d%n",
//                                       a.getFirstName(), a.getLastName(), a.getBooks().size());
//                           });
//                   break;
//               case "4":
//                   this.bookService
//                           .findAllBooksWithAuthorGeorgePowell()
//                           .forEach(b -> {
//                               System.out.printf("%s %s %d%n",
//                                       b.getTitle(), b.getReleaseDate(), b.getCopies());
//                           });
//                   break;
//               default:
//                   System.out.println("The exercise are between 1 to 4");
//           }
//           System.out.print("Enter your numbere here: ");
//           input = reader.readLine();
//       }

        System.out.println("Choose from 1 to 11 to check my exercise, please be nice with me :)");
        System.out.printf("Chose 0 for exit\r\n");
        System.out.print("Enter your number here: ");


        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));


        String input = reader.readLine();

        while (!"0".equals(input)) {
            switch (input) {

                case "1":
                    this.bookService.printAllBooksByAgeRestriction(reader.readLine());
                    break;
                case "2":
                    this.bookService.printAllBooksByEditionTypeAndCopies();
                    break;
                case "3":
                    this.bookService.printAllBooksByPriceInBounds();
                    break;
                case "4":
                    this.bookService.printAllBooksNotInYear("2000");
                    break;
                case "5":
                    this.bookService.printAllBooksBeforeDate(reader.readLine());
                    break;
                case "6":
                    this.authorService.printAllAuthorEndWith(reader.readLine());
                    break;
                case "8":
                    this.bookService.printAllBooksWithAuthorLastNameStarting(reader.readLine());
                    break;
                case "9":
                    this.bookService.printNumberBooksCountWithTitleLengthBigger(Integer.parseInt(reader.readLine()));
                    break;
                case "10":
                    this.authorService.printAllAuthorsByBookCopies();
                    break;
                case "11":
                    BookInfo bookByTitle = this.bookService.findBookByTitle(reader.readLine());
                    System.out.println(bookByTitle.getTitle());
                    System.out.println(bookByTitle.getCopies());
                    System.out.println(bookByTitle.getPrice());
                    break;
                default:
                    System.out.println("The exercise are between 1 to 11");
            }
            System.out.print("Enter your number here: ");
            input = reader.readLine();
        }
    }
}


        // ex.1
        // this.bookService.printAllBooksByAgeRestriction(reader.readLine());
        // ex.2
        //this.bookService.printAllBooksByEditionTypeAndCopies();
        //ex.3
        //this.bookService.printAllBooksByPriceInBounds();
        //ex.4
        //this.bookService.printAllBooksNotInYear("2000");
        //ex.5
        //this.bookService.printAllBooksBeforeDate(reader.readLine());
        //ex.6
        //this.authorService.printAllAuthorEndWith(reader.readLine());
        //ex.8
        //this.bookService.printAllBooksWithAuthorLastNameStarting(reader.readLine());
        //ex.9
        //this.bookService.printNumberBooksCountWithTitleLengthBigger(Integer.parseInt(reader.readLine()));
        //ex.10
        //this.authorService.printAllAuthorsByBookCopies();
        //ex.11
//        BookInfo bookByTitle = this.bookService.findBookByTitle(reader.readLine());
//        System.out.println(bookByTitle.getTitle());
//        System.out.println(bookByTitle.getCopies());
//        System.out.println(bookByTitle.getPrice());
        //ex.12
        //     this.bookService.printUpdatedCopiesCount(reader.readLine(), Integer.parseInt(reader.readLine()));




