package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor//final이 있는 녀석만! 생성자 생성!
public class MemberService {

    //@Autowired Injection이 없어도 이렇게! 해서 빈 주입해주면 된대
    private final MemberRepository memberRepository;

    //@Required... 하면 이거 없어도돼
    /*public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }*/

    //회원 가입
    @Transactional(readOnly = false)
    public Long join(Member member) {
        //==중복회원검증==//
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        //EXCEPTION (문제가있으면 터뜨려버리겠다)
        List<Member> findMembers = memberRepository.findByName(member.getUsername());
        //그리고 실무에서는 이름에 unique 제약 조건을.. 걸어주는게 좋대 ㅇㅅㅇ
        //동명이인의 경우가 있으니까..

        if(!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    //회원 전체 조회
    //@Transactional(readOnly = true)
    public List<Member> findMembers() {
        return memberRepository.findALL();
    }

    //회원 단건 조회
    //@Transactional(readOnly = true)
    public Member findMember(Long memberId) {
        return memberRepository.findOne(memberId);
    }
}
