package cs5031.group.one.thelibrary.repository;

import cs5031.group.one.thelibrary.model.Member;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends CrudRepository<Member, Long> {
    Member findMemberByEmailAddress(String emailAddress);
    Member findMemberByMemberId(Long id);
    Member deleteMemberByMemberId(Long id);
    Member deleteMemberByEmailAddress(String emailAddress);
    boolean existsMemberByMemberId(Long id);
    boolean existsMemberByEmailAddress(String emailAddress);
}
