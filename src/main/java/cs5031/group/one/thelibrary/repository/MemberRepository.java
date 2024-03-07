package cs5031.group.one.thelibrary.repository;

import cs5031.group.one.thelibrary.model.Member;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * This is repository interface for the member model of the library system.
 */
@Repository
public interface MemberRepository extends CrudRepository<Member, Long> {

    /**
     * This is the repository method to find member using member's email address.
     *
     * @param emailAddress This is the input parameter
     * @return The method returns a member object
     */
    Member findMemberByEmailAddress(String emailAddress);

    /**
     * This is the repository method to find member using member's id.
     *
     * @param id This is the input parameter
     * @return The method returns a member object
     */
    Member findMemberByMemberId(Long id);

    /**
     * This is the repository method to check existence of a member using member's email address.
     *
     * @param id This is the input parameter
     * @return This returns a boolean true or false depending on member's existence in the system.
     */
    boolean existsMemberByMemberId(Long id);

    /**
     * This is the repository method to check existence of a member using member's email address.
     *
     * @param emailAddress This is the input parameter
     * @return This returns a boolean true or false depending on member's existence in the system.
     */
    boolean existsMemberByEmailAddress(String emailAddress);
}
