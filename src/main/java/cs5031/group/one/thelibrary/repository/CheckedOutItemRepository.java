package cs5031.group.one.thelibrary.repository;

import cs5031.group.one.thelibrary.model.CheckedOutItem;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * This is repository interface for the checked out item model of the library system.
 */
@Repository
public interface CheckedOutItemRepository extends CrudRepository<CheckedOutItem, Long> {

    // Method for the repository to create a query that will return the book for
    // the combination of isbn + member id + return status = false
    // This returns a single borrowed record
    Optional<CheckedOutItem> findByBookAndMemberAndReturnStatusIsFalse(
            String book,
            Long member);

    // Method for the repository to create a query that will return the book for
    // the combination of isbn + member id + return status = true
    // This returns a single returned record
    Optional<CheckedOutItem> findByBookAndMemberAndReturnStatusIsTrue(
            String book,
            Long member);

    // Method for the repository to create a query that will return the book for a member
    List<CheckedOutItem> findByMemberAndReturnStatus(Long member, boolean returnStatus);

    // Method for the repository to create a query that will return the
    // checkedoutitem history
    @SuppressWarnings("null")
    List<CheckedOutItem> findAll();


    /**
     * Method for the repository to create a query that will return true of false if a member has one or more book checked out.
     * member id + return status = false
     *
     * @param member The member Id
     * @return Returns a boolean.
     */
    Optional<CheckedOutItem> findByMemberAndReturnStatusIsFalse(
            Long member);
}
