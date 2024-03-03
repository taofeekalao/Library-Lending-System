package cs5031.group.one.thelibrary.repository;

import cs5031.group.one.thelibrary.model.CheckedOutItem;

import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CheckedOutItemRepository extends CrudRepository<CheckedOutItem, Long> {

    Optional<CheckedOutItem> findByBookAndMemberAndReturnStatusIsFalse(
            String book,
            Long member);

    Optional<CheckedOutItem> findByBookAndMemberAndReturnStatusIsTrue(
            String book,
            Long member);

    @SuppressWarnings("null")
    List<CheckedOutItem> findAll();

}
