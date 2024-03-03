package cs5031.group.one.thelibrary;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import cs5031.group.one.thelibrary.model.CheckedOutItem;
import cs5031.group.one.thelibrary.model.LibraryModel;
import cs5031.group.one.thelibrary.repository.CheckedOutItemRepository;

@SpringBootTest
class TheLibraryApplicationTests {

    @Autowired
    private LibraryModel libraryModel;
    @Autowired
    private CheckedOutItemRepository checkedOutItemRepository;

    @Test
    void contextLoads() {
    }

    @Test
    public void testBorrowBookSuccess() {
        Long memberId = 00001L;
        boolean success = libraryModel.borrowBook("9780743273565", memberId);

        assertTrue(success);
    }

    @Test
    public void testBorrowBookFailure() {
        Long memberId = 00001L;
        boolean success = libraryModel.borrowBook("111", memberId);

        assertFalse(success);
    }

    @Test
    public void testReturnBookSuccess() {
        Long memberId = 00001L;
        libraryModel.borrowBook("9780743273565", memberId);
        boolean success = libraryModel.returnBook("9780743273565", memberId);

        assertTrue(success);
    }

    @Test
    public void testReturnBookFailure() {
        Long memberId = 00001L;
        boolean success = libraryModel.returnBook("111", memberId);

        assertFalse(success);
    }

    @Test
    public void testBorrowCheckedOutItemSuccess() {
        Long memberId = 00001L;
        boolean success = libraryModel.borrowBook("9780743273565", memberId);

        assertTrue(success);

        // Verify that a CheckedOutItem record was created
        Optional<CheckedOutItem> checkedOutItem = checkedOutItemRepository
                .findByBookAndMemberAndReturnStatusIsFalse("9780743273565", memberId);
        assertTrue(checkedOutItem.isPresent());
    }

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

    @Test
    public void testReturnCheckedOutItemSuccess() {
        Long memberId = 00001L;
        boolean success = libraryModel.returnBook("9780743273565", memberId);

        assertTrue(success);

        // Verify that a CheckedOutItem record was updated
        Optional<CheckedOutItem> checkedOutItem = checkedOutItemRepository
                .findByBookAndMemberAndReturnStatusIsTrue("9780743273565", memberId);
        assertTrue(checkedOutItem.isPresent());
    }

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
