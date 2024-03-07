package cs5031.group.one.thelibrary.service;

import cs5031.group.one.thelibrary.model.Book;
import cs5031.group.one.thelibrary.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * This is Book Service class for the book of the library system.
 */
@Service
public class BookDBService {
    // load repository
    private final BookRepository bookRepository;

    @Autowired
    public BookDBService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    /**
     * A list of books
     *
     * @return a list of books
     */
    public List<Book> getAllBooks() {
        // Get a list of all books (for the user to select one)
        Iterable<Book> allBooks = bookRepository.findAll();

        List<Book> bookList = new ArrayList<>();
        for (Book book : allBooks) {
            bookList.add(book);
        }

        return bookList;
    }

    /**
     * The book being requested
     *
     * @param isbn - the book being requested by the user
     * @return Book - the book retrieved from the database
     */
    public Book findBookByIsbn(String isbn) {
        // Retrieve the book using the isbn number
        Book book = bookRepository.findByIsbn(isbn);

        return book;
    }

    /**
     * The book being requested
     *
     * @param userBook - save the requested book back to the database
     */
    @Transactional
    public void updateBook(Book userBook) {
        // Save the book
        if (userBook != null) {
            bookRepository.save(userBook);
        }
    }

    /**
     * This is the method to add a new book item to the library system.
     *
     * @param newBook
     * @return
     */
    @Transactional
    public Book addBook(Book newBook) {
        // First, check if a book with the same ISBN already exists in the database
        if (newBook != null && bookRepository.findByIsbn(newBook.isbn()) == null) {
            // If the book does not exist, save the new book
            return bookRepository.save(newBook);
        } else {
            // If the book already exists, or the newBook object is null, return null
            return null;
        }
    }
}
