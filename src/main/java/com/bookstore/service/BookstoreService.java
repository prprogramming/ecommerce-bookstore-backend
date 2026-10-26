package com.ibm.specialist.bookstore.service;

import com.ibm.specialist.bookstore.model.Book;
import com.ibm.specialist.bookstore.model.CartItem;
import com.ibm.specialist.bookstore.model.Order;
import com.ibm.specialist.bookstore.repository.BookRepository;
import com.ibm.specialist.bookstore.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class BookstoreService {

    private final BookRepository bookRepository;
    private final OrderRepository orderRepository;

    public BookstoreService(BookRepository bookRepository, OrderRepository orderRepository) {
        this.bookRepository = bookRepository;
        this.orderRepository = orderRepository;
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }

    @Transactional
    public Order checkout(String customerEmail, List<CartItem> cartItems) {
        BigDecimal total = BigDecimal.ZERO;

        for (CartItem item : cartItems) {
            Book book = bookRepository.findById(item.getBook().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Book not found: " + item.getBook().getId()));

            if (book.getStockQuantity() < item.getQuantity()) {
                throw new IllegalStateException("Insufficient stock for book: " + book.getTitle());
            }

            // Deduct stock
            book.setStockQuantity(book.getStockQuantity() - item.getQuantity());
            bookRepository.save(book);

            total = total.add(book.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
        }

        Order order = new Order(customerEmail, total, "COMPLETED");
        return orderRepository.save(order);
    }
}
