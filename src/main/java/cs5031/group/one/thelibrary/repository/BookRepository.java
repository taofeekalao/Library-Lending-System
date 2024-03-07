package cs5031.group.one.thelibrary.repository;

import cs5031.group.one.thelibrary.model.Book;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * This is repository interface for the book model of the library system.
 */
@Repository
public interface BookRepository extends CrudRepository<Book, Long> {
    /**
     * Method for the repository to create a query that will return a book for
     * the requested isbn
     */
    Book findByIsbn(String isbn);

}
