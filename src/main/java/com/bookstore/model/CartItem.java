package com.ibm.specialist.bookstore.model;

import java.math.BigDecimal;

public class CartItem {
    
    private Book book;
    private Integer quantity;

    public CartItem() {}

    public CartItem(Book book, Integer quantity) {
        this.book = book;
        this.quantity = quantity;
    }

    public Book getBook() { return book; }
    public void setBook(Book book) { this.book = book; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    public BigDecimal getSubtotal() {
        if (book == null || book.getPrice() == null) return BigDecimal.ZERO;
        return book.getPrice().multiply(BigDecimal.valueOf(quantity));
    }
}
