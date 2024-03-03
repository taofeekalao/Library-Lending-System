package cs5031.group.one.thelibrary;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.util.List;

import cs5031.group.one.thelibrary.service.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import cs5031.group.one.thelibrary.controller.LibraryController;
import cs5031.group.one.thelibrary.model.Book;
import cs5031.group.one.thelibrary.model.CheckedOutItem;
import cs5031.group.one.thelibrary.model.LibraryModel;
import cs5031.group.one.thelibrary.repository.BookRepository;
import cs5031.group.one.thelibrary.service.BookDBService;
import cs5031.group.one.thelibrary.service.CheckedOutItemService;

import org.junit.jupiter.api.BeforeEach;

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

    @BeforeEach
    public void setUp() {
        libraryModel = new LibraryModel(bookRepository, checkedOutItemService);
        bookService = new BookDBService(bookRepository);
        libraryController = new LibraryController(libraryModel, bookService, checkedOutItemService, memberService);
    }

    @Test
    void contextLoads() {
    }

    @Test
    public void testBorrowBookSuccess() {
        Long memberId = 00001L;
        ResponseEntity<String> result = libraryController.borrowBook("9780451524935", memberId);
        assertEquals("Book borrowed", result.getBody());
    }

    @Test
    public void testBorrowBookFailure() {
        Long memberId = 00001L;
        ResponseEntity<String> result = libraryController.borrowBook("111", memberId);
        assertEquals("Borrow failed", result.getBody());
    }

    @Test
    public void testReturnBookSuccess() {
        Long memberId = 00001L;
        ResponseEntity<String> result = libraryController.returnBook("9780451524935", memberId);
        assertEquals("Book returned", result.getBody());
    }

    @Test
    public void testReturnBookFailure() {
        Long memberId = 00001L;
        ResponseEntity<String> result = libraryController.returnBook("111", memberId);
        assertEquals("Return failed", result.getBody());
    }

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
