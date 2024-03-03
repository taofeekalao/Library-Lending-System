package cs5031.group.one.thelibrary.service;

import cs5031.group.one.thelibrary.model.Member;

import java.util.List;
import java.util.Optional;

public interface MemberService {
    Optional<Member> findMemberById(Long id);
    Optional<Member> findMemberByEmailAddress(String emailAddress);
    Member deleteMemberById(Long id);
    Member deleteMemberByEmailAddress(String emailAddress);
    boolean existsMemberById(Long id);
    boolean existsMemberByEmailAddress(String emailAddress);
    Member addMemberToTheLibrary(Member member);
    List<Member> listAllMembers();
}
