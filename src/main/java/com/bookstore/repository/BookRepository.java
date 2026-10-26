package com.ibm.specialist.bookstore.repository;

import com.ibm.specialist.bookstore.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByAuthorContainingIgnoreCase(String author);
    List<Book> findByTitleContainingIgnoreCase(String title);
}
