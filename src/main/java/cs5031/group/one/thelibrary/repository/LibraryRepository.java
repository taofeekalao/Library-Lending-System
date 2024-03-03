package cs5031.group.one.thelibrary.repository;

import cs5031.group.one.thelibrary.model.Library;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LibraryRepository extends CrudRepository<Library, Long> {
}
