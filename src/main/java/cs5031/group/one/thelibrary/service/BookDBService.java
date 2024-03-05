package cs5031.group.one.thelibrary.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cs5031.group.one.thelibrary.model.Book;
import cs5031.group.one.thelibrary.repository.BookRepository;

// Book Service
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
}
