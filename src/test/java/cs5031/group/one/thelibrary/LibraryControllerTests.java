package cs5031.group.one.thelibrary;

import cs5031.group.one.thelibrary.controller.LibraryController;
import cs5031.group.one.thelibrary.model.Book;
import cs5031.group.one.thelibrary.model.CheckedOutItem;
import cs5031.group.one.thelibrary.model.LibraryModel;
import cs5031.group.one.thelibrary.repository.BookRepository;
import cs5031.group.one.thelibrary.repository.CheckedOutItemRepository;
import cs5031.group.one.thelibrary.service.BookDBService;
import cs5031.group.one.thelibrary.service.CheckedOutItemService;
import cs5031.group.one.thelibrary.service.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LibraryControllerTests {

    @Autowired
    private LibraryModel libraryModel;
    @Autowired
    private LibraryController libraryController;
    @Autowired
    private BookDBService bookService;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private CheckedOutItemService checkedOutItemService;
    @Autowired
    private MemberService memberService;
    @Autowired
    private CheckedOutItemRepository checkedOutItemRepository;

    @BeforeEach
    public void setUp() {
        libraryModel = new LibraryModel(bookRepository, checkedOutItemService);
        bookService = new BookDBService(bookRepository);
        libraryController = new LibraryController(libraryModel, bookService, checkedOutItemService, memberService,
                checkedOutItemRepository);
    }

    @Test
    void contextLoads() {
    }

    // Test to borrow a book with success
    @Test
    public void testBorrowBookSuccess() {
        Long memberId = 00001L;
        ResponseEntity<String> result = libraryController.borrowBook("9780451524935", memberId);
        assertEquals("Book borrowed", result.getBody());
    }

    // Test to borrow a book that should fail
    @Test
    public void testBorrowBookFailure() {
        Long memberId = 00001L;
        ResponseEntity<String> result = libraryController.borrowBook("111", memberId);
        assertEquals("Borrow failed", result.getBody());
    }

    // Test to return a book with success
    @Test
    public void testReturnBookSuccess() {
        Long memberId = 00001L;
        ResponseEntity<String> result = libraryController.returnBook("9780451524935", memberId);
        assertEquals("Book returned", result.getBody());
    }

    // Test to return a book that should fail
    @Test
    public void testReturnBookFailure() {
        Long memberId = 00001L;
        ResponseEntity<String> result = libraryController.returnBook("111", memberId);
        assertTrue("Return failed".equals(result.getBody())
                || "Book is not currently borrowed by the member".equals(result.getBody()));
    }

    // Test to ensure a book list cannot be empty
    @Test
    public void testGetBookList() {
        // Call the controller method
        ResponseEntity<List<Book>> responseEntity = libraryController.getBookList();

        // Verify the response
        int statusCode = responseEntity.getStatusCode().value();
        assertEquals(200, statusCode);
        List<Book> books = responseEntity.getBody();

        // Validate that the returned list is not null and not empty
        assertNotNull(books);
        assertNotEquals(0, books.size());
    }

    // Test to ensure a checkedoutitem history list cannot be empty
    @Test
    public void testGetCheckedOutList() {
        // Call the controller method
        ResponseEntity<List<CheckedOutItem>> responseEntity = libraryController.getCheckedOutList();

        // Verify the response
        int statusCode = responseEntity.getStatusCode().value();
        assertEquals(200, statusCode);
        List<CheckedOutItem> checkedOutItems = responseEntity.getBody();

        // Validate that the returned list is not null and not empty
        assertNotNull(checkedOutItems);
        assertNotEquals(0, checkedOutItems.size());
    }

}
