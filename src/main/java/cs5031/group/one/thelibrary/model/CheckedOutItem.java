package cs5031.group.one.thelibrary.model;

import org.springframework.data.annotation.Id;

import java.time.LocalDate;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
import java.util.Date;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

/**
 * This is a record class representing the checked out item.
 *
 * @param checkedOutBookItemId The ID of the record checked out.
 * @param checkoutDate         The checkout date of the item.
 * @param dueDate              The checkout date of the item.
 * @param returnDate           The return date of the item.
 * @param returnStatus         The return status of the item.
 * @param book                 The book item that is checked out.
 * @param member               The member checking out the book item.
 */

public record CheckedOutItem(@Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long checkedOutBookItemId,
        Long member, String book, LocalDate checkoutDate,
        Date dueDate, boolean returnStatus, Date returnDate) {
}
