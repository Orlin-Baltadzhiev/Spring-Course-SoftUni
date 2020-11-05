package com.softuni2.springintroex2.Services.Impl;

import com.softuni2.springintroex2.Constants.GlobalConstants;
import com.softuni2.springintroex2.Repositories.AuthorRepository;
import com.softuni2.springintroex2.Services.AuthorService;
import com.softuni2.springintroex2.Utils.FileUtil;
import com.softuni2.springintroex2.entities.Author;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

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
}
