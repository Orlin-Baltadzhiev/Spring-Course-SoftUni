package com.softuni2.springintroex2.Services.Impl;

import com.softuni2.springintroex2.domain.Repositories.AuthorRepository;
import com.softuni2.springintroex2.Services.AuthorService;
import com.softuni2.springintroex2.Utils.FileUtil;
import com.softuni2.springintroex2.domain.entities.Author;
import com.softuni2.springintroex2.domain.entities.Book;
import org.aspectj.apache.bcel.classfile.SourceFile;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

import static com.softuni2.springintroex2.Constants.GlobalConstants.*;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;
    private final FileUtil fileUtil;

    public AuthorServiceImpl(AuthorRepository authorRepository, FileUtil fileUtil) {
        this.authorRepository = authorRepository;
        this.fileUtil = fileUtil;
    }

    @Override
    public void seedAuthors() throws IOException {
        if(this.authorRepository.count() != 0){
            return;
        }

        String[] fileContent = this.fileUtil
                .readFileContent(AUTHORS_FILE_PATH);

        Arrays.stream(fileContent)
                .forEach(r -> {
                    String[] params = r.split("\\s+");
                    Author author = new Author(params[0],params[1]);

                    this.authorRepository.saveAndFlush(author);
                });

    }

    @Override
    public int getAllAuthorsCount() {
        return (int) this.authorRepository.count();
    }

    @Override
    public Author findAuthorById(Long id) {
        return this.authorRepository.getOne(id);
    }

    @Override
    public List<Author> findAllAuthorsByCountOfBooks() {
        return this.authorRepository.findAuthorByCountOfBook();
    }

    @Override
    public List<Author> findAllAuthorsWithBooksBefore() {
        List<Author> authorByBooksBefore = this.authorRepository.booksBefore1990();
        return authorByBooksBefore;
    }

    @Override
    public void printAllAuthorEndWith(String end) {
        this.authorRepository.findAllByFirstNameEndingWith(end)
                .forEach(a -> System.out.printf("%s %s%n",a.getFirstName(),a.getLastName()));
    }

    @Override
    public void printAllAuthorsByBookCopies() {
        List<Author> authors = this.authorRepository.findAll();
        Map<String, Integer> authorCopies = new HashMap<>();
        authors.forEach(author -> {
            int copies =author.getBooks()
                    .stream()
                    .mapToInt(Book::getCopies)
                    .sum();
            authorCopies.put(author.getFirstName() + " "  + author.getLastName(), copies);
        });

        authorCopies
                .entrySet()
                .stream().
                sorted((first,second) -> Integer.compare(second.getValue(), first.getValue()))
                .forEach(author ->
                        System.out.printf("%s %d%n", author.getKey(),author.getValue()));


    }


}
