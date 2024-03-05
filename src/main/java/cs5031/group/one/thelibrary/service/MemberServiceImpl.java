package cs5031.group.one.thelibrary.service;

import cs5031.group.one.thelibrary.model.Member;
import cs5031.group.one.thelibrary.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * This is the service implementation of the member service created for the model of the library system.
 */
@Service
public class MemberServiceImpl implements MemberService {

    MemberRepository memberRepository;

    /**
     * This is the constructor of the class.
     *
     * @param memberRepository This is the repository object as a parameter.
     */
    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    /**
     * This is the service interface method to find member using member's id.
     *
     * @param id This is the input parameter
     * @return The method returns a member object
     */
    @Override
    public Optional<Member> findMemberById(Long id) {
        return Optional.ofNullable(memberRepository.findMemberByMemberId(id));
    }

    /**
     * This is the service implementation method to find member using member's email address.
     *
     * @param emailAddress This is the input parameter
     * @return The method returns a member object
     */
    @Override
    public Optional<Member> findMemberByEmailAddress(String emailAddress) {
        return Optional.ofNullable(memberRepository.findMemberByEmailAddress(emailAddress));
    }


    /**
     * This is the service implementation method to delete member using member's id.
     *
     * @param id This is the input parameter
     * @return The method returns a member object
     */
    @Override
    public Member deleteMemberById(Long id) {
        if (existsMemberById(id)) {
            return memberRepository.deleteMemberByMemberId(id);
        }
        return null;
    }

    /**
     * This is the service implementation method to delete member using member's email address.
     *
     * @param emailAddress This is the input parameter
     * @return The method returns a member object
     */
    @Override
    public Member deleteMemberByEmailAddress(String emailAddress) {
        if (existsMemberByEmailAddress(emailAddress)) {
            return memberRepository.deleteMemberByEmailAddress(emailAddress);
        }
        return null;
    }


    /**
     * This is the service implementation method to check existence of a member using member's email address.
     *
     * @param id This is the input parameter
     * @return This returns a boolean true or false depending on member's existence in the system.
     */
    @Override
    public boolean existsMemberById(Long id) {
        return memberRepository.existsMemberByMemberId(id);
    }


    /**
     * This is the service implementation method to check existence of a member using member's email address.
     *
     * @param emailAddress This is the input parameter
     * @return This returns a boolean true or false depending on member's existence in the system.
     */
    @Override
    public boolean existsMemberByEmailAddress(String emailAddress) {
        return memberRepository.existsMemberByEmailAddress(emailAddress);
    }

    /**
     * This is the service implementation method to add a new member to the library system.
     *
     * @param member This is the input parameter
     * @return This returns a member object created.
     */
    @SuppressWarnings("null")
    @Override
    public Member addMemberToTheLibrary(Member member) {
        return memberRepository.save(member);
    }

    /**
     * This is the service implementation method to list all members in the library system.
     *
     * @return This returns a list of all member objects in the system.
     */
    @Override
    public List<Member> listAllMembers() {
        Iterable<Member> allMembers = memberRepository.findAll();
        List<Member> memberList = new ArrayList<>();
        for (Member member : allMembers) {
            memberList.add(member);
        }
        return memberList;
    }

    /**
     * This is the service implementation method to update details of member.
     *
     * @param member This is the input parameter
     * @return This returns a member object created.
     */
    @Override
    public Member updateMemberDetail(Member member) {
        return memberRepository.save(member);
    }
}
