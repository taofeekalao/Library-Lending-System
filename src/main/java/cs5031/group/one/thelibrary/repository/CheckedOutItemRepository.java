package cs5031.group.one.thelibrary.repository;

import cs5031.group.one.thelibrary.model.CheckedOutItem;

import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CheckedOutItemRepository extends CrudRepository<CheckedOutItem, Long> {

        // Method for the respositoy to create a query that will return the book for
        // the combination of isbn + member id + return status = false
        // This returns a single borrowed record
        Optional<CheckedOutItem> findByBookAndMemberAndReturnStatusIsFalse(
                        String book,
                        Long member);

        // Method for the respositoy to create a query that will return the book for
        // the combination of isbn + member id + return status = true
        // This returns a single returned record
        Optional<CheckedOutItem> findByBookAndMemberAndReturnStatusIsTrue(
                        String book,
                        Long member);

        // Method for the respositoy to create a query that will return the book for a member
        List<CheckedOutItem> findByMemberAndReturnStatus(Long member, boolean returnStatus);

        // Method for the respositoy to create a query that will return the
        // checkedoutitem history
        @SuppressWarnings("null")
        List<CheckedOutItem> findAll();

}
