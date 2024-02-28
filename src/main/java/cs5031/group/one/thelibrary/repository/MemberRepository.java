package cs5031.group.one.thelibrary.repository;

import cs5031.group.one.thelibrary.model.Member;
import org.springframework.data.repository.CrudRepository;

public interface MemberRepository extends CrudRepository<Member, Long> {
}
