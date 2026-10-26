package com.ibm.specialist.bookstore.service;

import com.ibm.specialist.bookstore.model.Book;
import com.ibm.specialist.bookstore.model.CartItem;
import com.ibm.specialist.bookstore.model.Order;
import com.ibm.specialist.bookstore.repository.BookRepository;
import com.ibm.specialist.bookstore.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class BookstoreServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private BookstoreService bookstoreService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSuccessfulCheckout() {
        Book sampleBook = new Book("Cloud Native Java", "IBM Author", "123-456", new BigDecimal("49.99"), 10);
        sampleBook.setId(1L);

        when(bookRepository.findById(1L)).thenReturn(Optional.of(sampleBook));
        when(orderRepository.save(any(Order.class))).thenAnswer(i -> i.getArguments()[0]);

        CartItem item = new CartItem(sampleBook, 2);
        Order result = bookstoreService.checkout("test@ibm.com", List.of(item));

        assertNotNull(result);
        assertEquals("COMPLETED", result.getStatus());
        assertEquals(new BigDecimal("99.98"), result.getTotalPrice());
        assertEquals(8, sampleBook.getStockQuantity());
    }
}
