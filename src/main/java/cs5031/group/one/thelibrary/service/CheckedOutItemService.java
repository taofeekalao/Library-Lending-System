package cs5031.group.one.thelibrary.service;

import cs5031.group.one.thelibrary.model.CheckedOutItem;
import cs5031.group.one.thelibrary.repository.CheckedOutItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * This is the service interface of the checked out item model of the library system.
 */
@Service
public class CheckedOutItemService {

    private final CheckedOutItemRepository checkedOutItemRepository;

    /**
     * This is the constructor for the checked out item class.
     *
     * @param checkedOutItemRepository
     */
    @Autowired
    public CheckedOutItemService(CheckedOutItemRepository checkedOutItemRepository) {
        this.checkedOutItemRepository = checkedOutItemRepository;
    }

    /**
     * This is the method for a new checked out item.
     *
     * @param isbn     This is the isbn of the book item as a parameter.
     * @param memberId This is the member id as a parameter.
     * @return This returns the item checked out record.
     */
    @Transactional
    public boolean newCheckedOutItem(String isbn, Long memberId) {
        boolean checkedOut = false;
        try {
            LocalDate borrowDate = LocalDate.now();
            LocalDate dueDate = borrowDate.plusDays(30);

            CheckedOutItem checkedOutItem = new CheckedOutItem(
                    null,
                    memberId,
                    isbn,
                    borrowDate,
                    Date.valueOf(dueDate),
                    false,
                    null);

            checkedOutItemRepository.save(checkedOutItem);
            checkedOut = true;
        } catch (Exception e) {
            System.out.println("usage: unable to create CheckedOutItem " + e);
        }
        return checkedOut;
    }

    /**
     * This is the method for returning a checked out book item.
     *
     * @param isbn     This is the isbn of the book item as a parameter.
     * @param memberId This is the member id as a parameter.
     * @return This returns the item checked in record.
     */
    @Transactional
    public boolean returnCheckedOutItem(String isbn, Long memberId) {
        boolean returnCheckedOut = false;

        try {
            Optional<CheckedOutItem> optionalCheckedOutItem = checkedOutItemRepository
                    .findByBookAndMemberAndReturnStatusIsFalse(
                            isbn,
                            memberId);

            if (optionalCheckedOutItem.isPresent()) {
                CheckedOutItem checkedOutItem = optionalCheckedOutItem.get();
                checkedOutItem = new CheckedOutItem(
                        checkedOutItem.checkedOutBookItemId(),
                        checkedOutItem.member(),
                        checkedOutItem.book(),
                        checkedOutItem.checkoutDate(),
                        checkedOutItem.dueDate(),
                        true,
                        Date.valueOf(LocalDate.now()));

                checkedOutItemRepository.save(checkedOutItem);
                returnCheckedOut = true;
            }
        } catch (Exception e) {
            System.out.println("usage: unable to find the CheckedOutItem " + e);
        }
        return returnCheckedOut;
    }

    /**
     * This is the method that returns the list of all checked out items.
     *
     * @return The method returns a list of checked out items.
     */
    @Transactional
    public List<CheckedOutItem> getAllCheckedOutItems() {
        List<CheckedOutItem> allCheckedOutItems = new ArrayList<>();
        try {
            allCheckedOutItems = checkedOutItemRepository.findAll();

        } catch (Exception e) {
            System.out.println("usage: unable to retrieve all checked-out items: " + e);
        }
        return allCheckedOutItems;
    }

    /**
     * This is the method that returns the list of items checked out by a member.
     *
     * @param memberId     The is the member's id of the member as a parameter.
     * @param returnStatus The method returns a list of checked out items by a member.
     */
    @Transactional
    public List<CheckedOutItem> getCheckedOutItemsByMember(Long memberId, boolean returnStatus) {
        List<CheckedOutItem> checkedOutItems = new ArrayList<>();
        try {
            checkedOutItems = checkedOutItemRepository.findByMemberAndReturnStatus(memberId, returnStatus);
        } catch (Exception e) {
            System.out.println("usage: unable to retrieve checked-out items by member: " + e);
        }
        return checkedOutItems;
    }
}
