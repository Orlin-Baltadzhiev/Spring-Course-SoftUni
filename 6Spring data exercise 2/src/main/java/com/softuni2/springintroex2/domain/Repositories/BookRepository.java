package com.softuni2.springintroex2.domain.Repositories;

import com.softuni2.springintroex2.domain.entities.AgeRestriction;
import com.softuni2.springintroex2.domain.entities.Book;
import com.softuni2.springintroex2.domain.entities.EditionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;


@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
        List<Book> findAllByReleaseDateAfter(LocalDate localDate);

        List<Book>findAllByReleaseDateBefore(LocalDate localDate);

        @Query("SELECT bo FROM Book AS bo JOIN bo.author AS a WHERE" +
                " a.firstName = 'George' AND a.lastName = 'Powell' " +
                "ORDER BY bo.releaseDate DESC, bo.title ASC")
        List<Book> booksWithAuthorGeorgePowell();

        Set<Book> findAllByAgeRestriction(AgeRestriction ageRestriction);

        Set<Book> findAllByEditionTypeAndCopiesIsLessThan(EditionType type, int copies);

        Set<Book> findAllByPriceLessThanOrPriceGreaterThan(BigDecimal lowerBand, BigDecimal upperBand);

        @Query("SELECT b FROM Book  b WHERE  SUBSTRING(b.releaseDate, 0, 4) NOT LIKE :year")
        Set<Book> findAllBooksNotInYear(String year);

        Set<Book> findAllByReleaseDateIsLessThan(LocalDate date);

        Set<Book> findAllByAuthorLastNameStartingWith(String start);

        @Query("SELECT count(b) FROM Book b WHERE LENGTH(b.title) > :length")
        int getNumberOfBooksWithTitleLength(int length);

        Book findByTitle(String title);

        @Transactional
        @Modifying
        @Query("UPDATE Book b SET b.copies = b.copies + :copies WHERE b.releaseDate > :date")

        int updateCopies(int copies, LocalDate date);


}
