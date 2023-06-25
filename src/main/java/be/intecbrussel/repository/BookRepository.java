package be.intecbrussel.repository;

import be.intecbrussel.model.Book;
import be.intecbrussel.model.Status;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BookRepository implements BookRepositoryInterface {
    private static BookRepository bookRepositoryInstance;

    private static List<Book> listOfBooks;

    private BookRepository() {
        listOfBooks = new ArrayList<>();
        listOfBooks.add(new Book("To Kill a Mockingbird", "Harper Lee", "9780446310789", 1960));
        listOfBooks.add(new Book("The Great Gatsby", "Scott Fitzgerald", "9780743273565", 1925));
        listOfBooks.add(new Book("Pride and Prejudice", "Jane Austen", "9780486284736", 1813));
        listOfBooks.add(new Book("The Lord of te Rings", "J.R.R Tolkien", "9780544003415", 1955));
        listOfBooks.add(new Book("The Hobbit", "J.R.R Tolkien", "9780547928227", 1937));
    }

    public static BookRepository bookRepository(){
        if(bookRepositoryInstance == null){
            bookRepositoryInstance = new BookRepository();
        }
        return bookRepositoryInstance;
    }

    public void changeStatus(long id, Status status){
        if (searchById(id).isPresent()){
            searchById(id).get().setStatus(status);
        }
    }

    public  List<Book> getListOfBooks() {
        return listOfBooks;
    }

    public void addBook(Book book) {
        listOfBooks.add(book);
    }
    public void removeBook(long id){
        listOfBooks.removeIf(book -> book.getBookId() == id);
    }
    public  List<Book> getBooksByTitle(String search){
        return listOfBooks.stream().filter(a -> a.getTitle().equals(search)).toList();
    }
    public  List<Book> getBooksByIsbn(String search){
        return listOfBooks.stream().filter(a -> a.getIsbn().equals(search)).toList();
    }
    public  List<Book> getBooksByYearOfPublication(int search){
        return listOfBooks.stream().filter(a -> a.getYearOfPublication() == search).toList();
    }
    public  List<Book> getBooksByAuthor(String search){
        return listOfBooks.stream().filter(a -> a.getAuthor().equals(search)).toList();
    }
    public  Optional<Book> searchById(long search){
        return listOfBooks.stream().filter(book -> search == book.getBookId()).findFirst();
    }

    public boolean bookExists(long id){
        return searchById(id).isPresent();
    }


}