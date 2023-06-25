package be.intecbrussel.repository;

import be.intecbrussel.model.Book;
import be.intecbrussel.model.Status;

import java.util.List;
import java.util.Optional;

public interface BookRepositoryInterface {

    public void changeStatus(long id, Status status);

    public List<Book> getListOfBooks();

    public void addBook(Book book);

    public void removeBook(long id);

    public  List<Book> getBooksByTitle(String search);

    public  List<Book> getBooksByIsbn(String search);

    public  List<Book> getBooksByYearOfPublication(int search);

    public  List<Book> getBooksByAuthor(String search);

    public Optional<Book> searchById(long search);

    public boolean bookExists(long id);

}
