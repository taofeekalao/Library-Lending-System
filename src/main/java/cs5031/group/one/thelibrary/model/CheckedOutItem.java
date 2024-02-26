package cs5031.group.one.thelibrary.model;

import org.springframework.data.annotation.Id;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;


/**
 * This is a record class representing the checked out item.
 * @param checkedOutBookItemId The ID of the record checked out.
 * @param checkOutDate The checkout date of the item.
 * @param dueDate The checkout date of the item.
 * @param returnedDate The return date of the item.
 * @param bookCheckedOut The book item that is checked out.
 * @param memberCheckingOutBook The member checking out the book item.
 */
public record CheckedOutItem(@Id Long checkedOutBookItemId, DateTimeFormat checkOutDate, Date dueDate, Date returnedDate, Book bookCheckedOut, Member memberCheckingOutBook) {

}
