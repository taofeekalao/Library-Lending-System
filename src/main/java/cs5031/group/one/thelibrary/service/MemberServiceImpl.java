package cs5031.group.one.thelibrary.service;

import cs5031.group.one.thelibrary.model.Member;
import cs5031.group.one.thelibrary.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MemberServiceImpl implements MemberService {

    MemberRepository memberRepository;

    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public Optional<Member> findMemberById(Long id) {
        return Optional.ofNullable(memberRepository.findMemberByMemberId(id));
    }

    @Override
    public Optional<Member> findMemberByEmailAddress(String emailAddress) {
        return Optional.ofNullable(memberRepository.findMemberByEmailAddress(emailAddress));
    }

    @Override
    public Member deleteMemberById(Long id) {
        if (existsMemberById(id)) {
            return memberRepository.deleteMemberByMemberId(id);
        }
        return null;
    }

    @Override
    public Member deleteMemberByEmailAddress(String emailAddress) {
        if (existsMemberByEmailAddress(emailAddress)) {
            return memberRepository.deleteMemberByEmailAddress(emailAddress);
        }
        return null;
    }

    @Override
    public boolean existsMemberById(Long id) {
        return memberRepository.existsMemberByMemberId(id);
    }

    @Override
    public boolean existsMemberByEmailAddress(String emailAddress) {
        return memberRepository.existsMemberByEmailAddress(emailAddress);
    }

    @SuppressWarnings("null")
    @Override
    public Member addMemberToTheLibrary(Member member) {
        return memberRepository.save(member);
    }

    @Override
    public List<Member> listAllMembers() {
        Iterable<Member> allMembers = memberRepository.findAll();
        List<Member> memberList = new ArrayList<>();
        for (Member member : allMembers) {
            memberList.add(member);
        }
        return memberList;
    }
}
