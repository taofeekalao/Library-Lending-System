package cs5031.group.one.thelibrary.service;

import cs5031.group.one.thelibrary.model.Member;

import java.util.List;
import java.util.Optional;

/**
 * This is the service interface of the member model of the library system.
 */
public interface MemberService {


    /**
     * This is the service interface method to find member using member's id.
     * @param id This is the input parameter
     * @return The method returns a member object
     */
    Optional<Member> findMemberById(Long id);

    /**
     * This is the service interface method to find member using member's email address.
     *
     * @param emailAddress This is the input parameter
     * @return The method returns a member object
     */
    Optional<Member> findMemberByEmailAddress(String emailAddress);

    /**
     * This is the service interface method to delete member using member's id.
     * @param id This is the input parameter
     * @return The method returns a member object
     */
    Member deleteMemberById(Long id);

    /**
     * This is the service interface method to delete member using member's email address.
     * @param emailAddress This is the input parameter
     * @return The method returns a member object
     */
    Member deleteMemberByEmailAddress(String emailAddress);

    /**
     * This is the service interface method to check existence of a member using member's email address.
     * @param id This is the input parameter
     * @return This returns a boolean true or false depending on member's existence in the system.
     */
    boolean existsMemberById(Long id);

    /**
     * This is the service interface method to check existence of a member using member's email address.
     * @param emailAddress This is the input parameter
     * @return This returns a boolean true or false depending on member's existence in the system.
     */
    boolean existsMemberByEmailAddress(String emailAddress);

    /**
     * This is the service interface method to add a new member to the library system.
     * @param member This is the input parameter
     * @return This returns a member object created.
     */
    Member addMemberToTheLibrary(Member member);

    /**
     * This is the service interface method to list all members in the library system.
     *
     * @return This returns a list of all member objects in the system.
     */
    List<Member> listAllMembers();

    /**
     * This is the service interface method to update details of member.
     * @param member This is the input parameter
     * @return This returns a member object created.
     */
    Member updateMemberDetail(Member member);
}
