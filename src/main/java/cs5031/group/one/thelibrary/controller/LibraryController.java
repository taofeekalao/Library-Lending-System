package cs5031.group.one.thelibrary.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import cs5031.group.one.thelibrary.model.Book;
import cs5031.group.one.thelibrary.model.CheckedOutItem;
import cs5031.group.one.thelibrary.model.LibraryModel;
import cs5031.group.one.thelibrary.service.BookDBService;
import cs5031.group.one.thelibrary.service.CheckedOutItemService;
import cs5031.group.one.thelibrary.model.Member;
import cs5031.group.one.thelibrary.service.MemberService;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * This is the controller of the application.
 */
@RestController
public class LibraryController {
    private final LibraryModel libraryModel;
    private final BookDBService bookService;
    private final CheckedOutItemService checkedOutItemService;
    private final MemberService memberService;

    @Autowired
    public LibraryController(LibraryModel libraryModel, BookDBService bookService, CheckedOutItemService checkedOutItemService, MemberService memberService) {
        this.libraryModel = libraryModel;
        this.bookService = bookService;
        this.checkedOutItemService = checkedOutItemService;
        this.memberService = memberService;
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

/**
     * This is the post method to add a new member to the list of members who subscribed to the library service.
     *
     * @param newMember            The member object with the detail of the member as object properties.
     * @param uriComponentsBuilder The URI builder to add the location of the newly created resource to the returned data.
     * @return The method returns a response entity object.
     */
    @PostMapping("/library/member")
    private ResponseEntity<Void> createMember(@RequestBody Member newMember, UriComponentsBuilder uriComponentsBuilder) {
        Member newLibraryMember = new Member(null, newMember.memberName(), newMember.address(), newMember.emailAddress());
        Optional<Member> existingMember = memberService.findMemberByEmailAddress((newMember.emailAddress()));
        if (existingMember.isPresent()) {
            return ResponseEntity.badRequest().build();
        } else {
            Member savedMember = memberService.addMemberToTheLibrary(newLibraryMember);
            URI locationOfNewMember = uriComponentsBuilder
                    .path("/library/member/{memberId}")
                    .buildAndExpand(savedMember.memberId())
                    .toUri();
            return ResponseEntity.created(locationOfNewMember).build();
        }

    }

    /**
     * This is the get method to retrieve a member from the list of members who subscribed to the library service.
     *
     * @param requestId This is the id of the member to be retrieved
     * @return The method returns a response entity object.
     */
    @GetMapping("/library/member/{requestId}")
    private ResponseEntity<Optional<Member>> findByMemberId(@PathVariable Long requestId) {
        Optional<Member> member = memberService.findMemberById(requestId);
        if (member.isPresent()) {
            return ResponseEntity.ok(member);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    /**
     * This is the delete method that removes member from the library using either member's id or member's email address.
     * @param requestId The request parameter - either the member's id or the member's email address'
     * @return The method returns a response entity object.
     */
    @DeleteMapping("/library/member/{requestId}")
    protected ResponseEntity<Void> deleteMemberById(@PathVariable String requestId) {
        if (requestId.contains("@")) {
            if (memberService.existsMemberByEmailAddress(requestId)) {
                memberService.deleteMemberByEmailAddress(requestId);
                return ResponseEntity.noContent().build();
            }
        }

        long id = 0L;
        try {
            id = Long.parseLong(requestId);
            if (memberService.existsMemberById(id)) {
                memberService.deleteMemberById(id);
                return ResponseEntity.noContent().build();
            }
        } catch (NumberFormatException e) {
            throw new RuntimeException(e);
        }
        ResponseEntity.badRequest();
        return ResponseEntity.notFound().build();
    }


    /**
     * This is the method that gets all the members in the system and returns a list of members.
     * @return This returns a list of members.
     */
    @GetMapping("/library/member/")
    private ResponseEntity<List<Member>> findAll() {
        List<Member> memberList = memberService.listAllMembers();
        System.out.println("Member List: " + memberList);
        return ResponseEntity.ok(memberList);
    }

    /**
     * This is the method that updates either the name or the address of a member,
     * @param requestedId The is the id of the member to be updated.
     * @param memberUpdate This is the request body of the member to be updated.
     * @return Return a response entity object which could be success or failure depending on the outcome of the transaction.
     */
    @PutMapping("/library/member/{requestedId}")
    private ResponseEntity<Void> updateMemberNameOrAddress(@PathVariable Long requestedId, @RequestBody Member memberUpdate) {
        Optional<Member> member = memberService.findMemberById(requestedId);
        if (member.isPresent()) {
            String newMemberName = null;
            if (memberUpdate.memberName() != null) {
                newMemberName = memberUpdate.memberName();
            }

            String newMemberAddress = null;
            if (memberUpdate.address() != null) {
                newMemberAddress = memberUpdate.address();
            }
            Member updatedMember = new Member(member.get().memberId(), newMemberName,  newMemberAddress, member.get().emailAddress());
            memberService.updateMemberDetail(updatedMember);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
