package cs5031.group.one.thelibrary.repository;

import cs5031.group.one.thelibrary.model.CheckedOutItem;
import org.springframework.data.repository.CrudRepository;

public interface CheckedOutItemRepository extends CrudRepository<CheckedOutItem, Long> {
}
