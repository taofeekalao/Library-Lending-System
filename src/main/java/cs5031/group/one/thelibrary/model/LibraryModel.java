package cs5031.group.one.thelibrary.model;

import cs5031.group.one.thelibrary.repository.BookRepository;
import cs5031.group.one.thelibrary.service.BookDBService;
import cs5031.group.one.thelibrary.service.CheckedOutItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Model class for the CS5031 P2 library assignment
 */
@Component
public class LibraryModel {

    private final BookDBService bookDBService;
    private final CheckedOutItemService checkedOutItemService;

    /**
     * This is the model class for the library entity.
     *
     * @param bookRepository
     * @param checkedOutItemService
     */
    @Autowired
    public LibraryModel(BookRepository bookRepository, CheckedOutItemService checkedOutItemService) {
        this.bookDBService = new BookDBService(bookRepository);
        this.checkedOutItemService = checkedOutItemService;
    }

    /**
     * The controller asks the model to perform the borrow book steps.
     * Update the book record and add an entry into the checkedoutitems history
     *
     * @param isbn     - isbn number
     * @param memberId - member id
     * @return boolean
     */
    public boolean borrowBook(String isbn, Long memberId) {
        boolean borrowed = false;

        try {
            Book book = bookDBService.findBookByIsbn(isbn);

            if (book != null && book.getQuantityIn() > 0) {
                // Get quantities
                int newQuantityIn = book.getQuantityIn() - 1;
                int newQuantityOut = book.getQuantityOut() + 1;

                // Update quantities
                Book updatedBook = book.withUpdatedQuantities(newQuantityIn, newQuantityOut);

                // Create CheckedOutItem
                boolean checkedOut = checkedOutItemService.newCheckedOutItem(isbn, memberId);

                if (checkedOut) {
                    bookDBService.updateBook(updatedBook);
                    borrowed = true;
                }
            }
        } catch (Exception e) {
            System.out.println("usage: unable to update book quantities " + e);
            borrowed = false;
        }
        return borrowed;
    }

    /**
     * The controller asks the model to perform the return book steps.
     * <p>
     * Update the book record and update the entry into the checkedoutitems history
     *
     * @param isbn     - isbn number
     * @param memberId - member id
     * @return boolean
     */
    public boolean returnBook(String isbn, Long memberId) {
        boolean returned = false;

        try {
            Book book = bookDBService.findBookByIsbn(isbn);

            if (book != null && book.getQuantityOut() > 0) {
                // Get quantities
                int newQuantityIn = book.getQuantityIn() + 1;
                int newQuantityOut = book.getQuantityOut() - 1;

                // Update quantities
                Book updatedBook = book.withUpdatedQuantities(newQuantityIn, newQuantityOut);

                // Update the checked-out item
                boolean returnCheckedOut = checkedOutItemService.returnCheckedOutItem(isbn, memberId);

                if (returnCheckedOut) {
                    // Save changes
                    bookDBService.updateBook(updatedBook);
                    returned = true;
                }
            }
        } catch (Exception e) {
            System.out.println("usage: unable to complete the book return " + e);
            returned = false;
        }

        return returned;
    }
}
