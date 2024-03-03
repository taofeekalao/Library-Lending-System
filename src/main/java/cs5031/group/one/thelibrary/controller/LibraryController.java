package cs5031.group.one.thelibrary.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import cs5031.group.one.thelibrary.model.Book;
import cs5031.group.one.thelibrary.model.CheckedOutItem;
import cs5031.group.one.thelibrary.model.LibraryModel;
import cs5031.group.one.thelibrary.service.BookDBService;
import cs5031.group.one.thelibrary.service.CheckedOutItemService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * This is the controller of the application.
 */
@RestController
public class LibraryController {
    private final LibraryModel libraryModel;
    private final BookDBService bookService;
    private final CheckedOutItemService checkedOutItemService;

    @Autowired
    public LibraryController(LibraryModel model, BookDBService bookService,
            CheckedOutItemService checkedOutItemService) {
        this.libraryModel = model;
        this.bookService = bookService;
        this.checkedOutItemService = checkedOutItemService;
    }

    /**
     * This is a default/basic home controller of the application.
     *
     * @return Return a string "Welcome to the library!".
     */
    @GetMapping("/")
    public String home() {
        return "Welcome to the library!";
    }

    @GetMapping("/borrowed")
    public ResponseEntity<String> borrowBook(
            @RequestParam String isbn,
            @RequestParam Long member_Id) {

        try {
            // Ask the LibraryModel to borrow a book
            // Validate input
            if (isbn == null || member_Id == null) {
                return ResponseEntity.badRequest().body("Invalid input");
            }

            boolean success = libraryModel.borrowBook(isbn, member_Id);

            if (success) {
                return ResponseEntity.ok("Book borrowed");
            } else {
                return ResponseEntity.badRequest().body("Borrow failed");
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error occurred");
        }
    }

    @GetMapping("/returned")
    public ResponseEntity<String> returnBook(
            @RequestParam String isbn,
            @RequestParam Long member_Id) {

        try {
            // Ask the LibraryModel to return a book
            // Validate input
            if (isbn == null || member_Id == null) {
                return ResponseEntity.badRequest().body("Invalid input");
            }

            boolean success = libraryModel.returnBook(isbn, member_Id);

            if (success) {
                return ResponseEntity.ok("Book returned");
            } else {
                return ResponseEntity.badRequest().body("Return failed");
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error occurred");
        }
    }

    @GetMapping("/bookList")
    public ResponseEntity<List<Book>> getBookList() {
        // Get a list of all books
        List<Book> bookList = bookService.getAllBooks();

        System.out.println("Book List:");
        for (Book book : bookList) {
            System.out.println("Book ID: " + book.bookId());
            System.out.println("ISBN: " + book.isbn());
            System.out.println("Title: " + book.title());
            System.out.println("Author: " + book.author());
            System.out.println("Quantity in Library: " + book.quantityInLibrary());
            System.out.println("Quantity Checked Out: " + book.quantityCheckedOut());
            System.out.println("-----");
        }

        return ResponseEntity.ok(bookList);
    }

    @GetMapping("/checkedOutList")
    public ResponseEntity<List<CheckedOutItem>> getCheckedOutList() {
        // Get a list of all checked-out items
        List<CheckedOutItem> checkedOutItemList = checkedOutItemService.getAllCheckedOutItems();

        System.out.println("Checked-Out List:");
        for (CheckedOutItem checkedOutItem : checkedOutItemList) {
            System.out.println("Checked-Out Item ID: " + checkedOutItem.checkedOutBookItemId());
            System.out.println("Member ID: " + checkedOutItem.member());
            System.out.println("Book ISBN: " + checkedOutItem.book());
            System.out.println("Checkout Date: " + checkedOutItem.checkoutDate());
            System.out.println("Due Date: " + checkedOutItem.dueDate());
            System.out.println("Return Status: " + checkedOutItem.returnStatus());
            System.out.println("Return Date: " + checkedOutItem.returnDate());
            System.out.println("-----");
        }

        return ResponseEntity.ok(checkedOutItemList);
    }
}
