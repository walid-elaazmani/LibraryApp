package be.intecbrussel.model;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    List<Book> listOfBooksInCart;

    public Cart() {
        this.listOfBooksInCart = new ArrayList<>();
    }

    public List<Book> getListOfBooksInCart() {
        return listOfBooksInCart;
    }

    public void addBookToCart(Book book) {
        this.listOfBooksInCart.add(book);
    }

    public void removeBook(Book book){
        listOfBooksInCart.remove(book);
    }

    public void clearCart(){
        listOfBooksInCart.clear();
    }

    @Override
    public String toString() {
        return "Cart{" +
                "listOfBooksInCart=" + listOfBooksInCart +
                '}';
    }
}
