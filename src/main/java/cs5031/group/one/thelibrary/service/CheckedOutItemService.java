package cs5031.group.one.thelibrary.service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cs5031.group.one.thelibrary.model.CheckedOutItem;
import cs5031.group.one.thelibrary.repository.CheckedOutItemRepository;

@Service
public class CheckedOutItemService {

    private final CheckedOutItemRepository checkedOutItemRepository;

    @Autowired
    public CheckedOutItemService(CheckedOutItemRepository checkedOutItemRepository) {
        this.checkedOutItemRepository = checkedOutItemRepository;
    }

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
