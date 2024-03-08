package cs5031.group.one.thelibrary;

import cs5031.group.one.thelibrary.model.CheckedOutItem;
import cs5031.group.one.thelibrary.model.LibraryModel;
import cs5031.group.one.thelibrary.repository.CheckedOutItemRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class TheLibraryApplicationTests {

    @Autowired
    private LibraryModel libraryModel;
    @Autowired
    private CheckedOutItemRepository checkedOutItemRepository;

    @Test
    void contextLoads() {
    }

    // Test the model logic for borrowing a book with success
    @Test
    public void testBorrowBookSuccess() {
        Long memberId = 00001L;
        boolean success = libraryModel.borrowBook("9780743273565", memberId);

        assertTrue(success);
    }

    // Test the model logic for borrowing a book that should fail
    @Test
    public void testBorrowBookFailure() {
        Long memberId = 00001L;
        boolean success = libraryModel.borrowBook("111", memberId);

        assertFalse(success);
    }

    // Test the model logic for returning a book
    @Test
    public void testReturnBookSuccess() {
        Long memberId = 00001L;
        libraryModel.borrowBook("9780061120084", memberId);
        boolean success = libraryModel.returnBook("9780061120084", memberId);

        assertTrue(success);
    }

    // Test the model logic for returning a book that should fail
    @Test
    public void testReturnBookFailure() {
        Long memberId = 00001L;
        boolean success = libraryModel.returnBook("111", memberId);

        assertFalse(success);
    }

    // Test the model logic for adding a borrowed book to the checked out history
    // this should pass
    @Test
    public void testBorrowCheckedOutItemSuccess() {
        Long memberId = 00003L;
        boolean success = libraryModel.borrowBook("9780061120084", memberId);

        assertTrue(success);

        // Verify that a CheckedOutItem record was created
        Optional<CheckedOutItem> checkedOutItem = checkedOutItemRepository
                .findByBookAndMemberAndReturnStatusIsFalse("9780061120084", memberId);
        assertTrue(checkedOutItem.isPresent());
    }

    // Test the model logic for adding a borrowed book to the checked out history
    // this should fail
    @Test
    public void testBorrowCheckedOutItemFailure() {
        Long memberId = 00001L;
        boolean success = libraryModel.borrowBook("111", memberId);

        assertFalse(success);

        // Verify that no CheckedOutItem record was created for an invalid book
        Optional<CheckedOutItem> checkedOutItem = checkedOutItemRepository
                .findByBookAndMemberAndReturnStatusIsFalse("111", memberId);
        assertFalse(checkedOutItem.isPresent());
    }

    // Test the model logic for returning book to the checked out history
    // this should pass
    @Test
    public void testReturnCheckedOutItemSuccess() {
        Long memberId = 00004L;
        boolean success = libraryModel.returnBook("9780590353427", memberId);

        assertTrue(success);

        // Verify that a CheckedOutItem record was updated
        Optional<CheckedOutItem> checkedOutItem = checkedOutItemRepository
                .findByBookAndMemberAndReturnStatusIsTrue("9780590353427", memberId);
        assertTrue(checkedOutItem.isPresent());
    }

    // Test the model logic for returning a borrowed book to the checked out history
    // this should fail
    @Test
    public void testReturnCheckedOutItemFailure() {
        Long memberId = 00001L;
        boolean success = libraryModel.returnBook("111", memberId);

        assertFalse(success);

        // Verify that no CheckedOutItem record was updated for an invalid book
        Optional<CheckedOutItem> checkedOutItem = checkedOutItemRepository
                .findByBookAndMemberAndReturnStatusIsTrue("111", memberId);
        assertFalse(checkedOutItem.isPresent());
    }

}
