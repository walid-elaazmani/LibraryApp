package be.intecbrussel.service;

import be.intecbrussel.Exceptions.InvalidInputException;
import be.intecbrussel.model.Book;
import be.intecbrussel.model.BorrowedBook;
import be.intecbrussel.model.Status;
import be.intecbrussel.model.User;
import be.intecbrussel.repository.BookRepository;
import be.intecbrussel.model.*;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class BookService {

    BookRepository bookRepository = BookRepository.bookRepository();

    public List<Book> getListOfBooks(){
        return bookRepository.getListOfBooks();
    }

    public void addBook(String title, String author, String isbn, int yearOfPublication) {
        if (checkBook(title, author, isbn, yearOfPublication)) {
            bookRepository.addBook(new Book(title, author, isbn, yearOfPublication));
        }
    }

    public void reserveBooks(User user) {
        if(user.getFine()>0){
            return;
        }

        for (Book cartBook : user.getCart().getListOfBooksInCart()) {

            if(user.getListOfActiveBorrowedBooks().size()>3){
               return;
            }

            changeStatus(cartBook.getBookId(), Status.RESERVED);
            BorrowedBook newBorrowedBook = new BorrowedBook(cartBook,user);
            newBorrowedBook.setActive(true);
            user.addBorrowedBook(newBorrowedBook);
            System.out.println(getBookById(cartBook.getBookId()));
            System.out.println(cartBook);
        }
        user.getCart().clearCart();
    }

    public void issueBookToUser(User user, Book book, LocalDate dateOfReturn){
        if(user == null || book == null || dateOfReturn == null || dateOfReturn.isBefore(LocalDate.now())){
            return;
        }

        if(getBookById(book.getBookId()).isEmpty()){
            return;
        }

        if(getBookById(book.getBookId()).get().getStatus().equals(Status.UNAVAILABLE)
                || getBookById(book.getBookId()).get().getStatus().equals(Status.RESERVED)){
            return;
        }

        if(user.getListOfActiveBorrowedBooks().size()>3){
            return;
        }

        changeStatus(book.getBookId(), Status.UNAVAILABLE);
        BorrowedBook newBorrowedBook = new BorrowedBook(book,user,dateOfReturn);
        newBorrowedBook.setActive(true);
        user.addBorrowedBook(newBorrowedBook);
    }

    private  boolean checkBook (String title, String author, String isbn, int yearOfPublication) {
        LocalDate date = LocalDate.now();
        int current_year = date.getYear();

        if (isbn.length() != 13) {
            return false;
        } else if (yearOfPublication < 1000 || yearOfPublication > current_year) {
            return false;
        } else if (author == null || author.isEmpty()) {
            return false;
        } else if (title == null || title.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    public void returnAllBooks(User user){
        for (BorrowedBook borrowedBook : user.getListOfActiveBorrowedBooks()) {
            changeStatus(borrowedBook.getBook().getBookId(), Status.UNAVAILABLE);
            borrowedBook.setActive(false);
        }
    }
    public void returnBook(User user, long id){
        for (BorrowedBook borrowedBook : user.getListOfActiveBorrowedBooks()) {
            if(borrowedBook.getBook().getBookId() == id){
                changeStatus(borrowedBook.getBook().getBookId(), Status.AVAILABLE);
                borrowedBook.setActive(false);
            }
        }
    }

    public void issueReservedBooks(User user){
        if(user.getFine()>0){
            return;
        }
        for (BorrowedBook borrowedBook : user.getListOfBorrowedBooks()) {
            if (borrowedBook.getBook().getStatus().equals(Status.RESERVED)){
                changeStatus(borrowedBook.getBook().getBookId(), Status.UNAVAILABLE);
                borrowedBook.setActive(true);
            }
        }
    }

    public void changeStatus(long id, Status status){
        bookRepository.changeStatus(id, status);
    }

    public void removeBook(long id){
        bookRepository.removeBook(id);
    }
    public Optional<Book> getBookById(long id){
        return bookRepository.searchById(id);
    }

    public List<Book> getBookByTitle(String title){
        return bookRepository.getBooksByTitle(title);
    }

    public List<Book> getBookByAuthor(String Author){
        return bookRepository.getBooksByAuthor(Author);
    }

    public List<Book> getBookByIsbn(String ISBN){
        return bookRepository.getBooksByIsbn(ISBN);
    }

    public List<Book> getBookByYearOfPublication(int yOP){
        return bookRepository.getBooksByYearOfPublication(yOP);
    }

    public List<Book> sortByTitle(List<Book> listToBeSorted){
        return listToBeSorted.stream()
                .sorted(Comparator.comparing(Book::getTitle))
                .collect(Collectors.toList());
    }

    public List<Book> sortByAuthor(List<Book> listToBeSorted){
        return listToBeSorted.stream()
                .sorted(Comparator.comparing(Book::getAuthor))
                .collect(Collectors.toList());
    }

    public List<Book> sortByISBN(List<Book> listToBeSorted){
        return listToBeSorted.stream()
                .sorted(Comparator.comparing(Book::getIsbn))
                .collect(Collectors.toList());
    }
}
