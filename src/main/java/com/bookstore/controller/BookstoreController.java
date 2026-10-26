package com.ibm.specialist.bookstore.controller;

import com.ibm.specialist.bookstore.model.Book;
import com.ibm.specialist.bookstore.model.CartItem;
import com.ibm.specialist.bookstore.model.Order;
import com.ibm.specialist.bookstore.service.BookstoreService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class BookstoreController {

    private final BookstoreService bookstoreService;

    public BookstoreController(BookstoreService bookstoreService) {
        this.bookstoreService = bookstoreService;
    }

    @GetMapping("/books")
    public ResponseEntity<List<Book>> getCatalog() {
        return ResponseEntity.ok(bookstoreService.getAllBooks());
    }

    @PostMapping("/books")
    public ResponseEntity<Book> addBook(@RequestBody Book book) {
        return ResponseEntity.ok(bookstoreService.saveBook(book));
    }

    @PostMapping("/orders/checkout")
    public ResponseEntity<Order> processCheckout(
            @RequestParam String customerEmail,
            @RequestBody List<CartItem> cartItems) {
        Order completedOrder = bookstoreService.checkout(customerEmail, cartItems);
        return ResponseEntity.ok(completedOrder);
    }
}
