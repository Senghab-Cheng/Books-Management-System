package com.example.demo.member;

import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public MemberResponse create(CreateMemberRequest request) {
        Member member = new Member();
        member.setName(request.name());
        member.setEmail(request.email());
        return MemberResponse.from(memberRepository.save(member));
    }

    public List<MemberResponse> list() {
        return memberRepository.findAll().stream().map(MemberResponse::from).toList();
    }
}
