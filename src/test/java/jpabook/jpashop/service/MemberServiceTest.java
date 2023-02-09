package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest //SpringBoot를 띄운 상태에서 테스트 할거야
@Transactional //rollback이 되니까
class MemberServiceTest {
    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;
    @Test
    public void 회원가입() throws Exception {
        //given
        Member member = new Member();
        member.setUsername("memberA");

        //when
        Long savedId = memberService.join(member);

        //then
        assertEquals(member, memberRepository.findOne(savedId));
    }

    @Test//(expected = IllegalStateException.class)
    public void 중복_회원_예약() throws Exception{
        Member member1 = new Member();
        member1.setUsername("kim1");

        Member member2 = new Member();
        member2.setUsername("kim1");

        //when
        memberService.join(member1);
        try {
            memberService.join(member2);
        } catch (IllegalStateException e) {
            return;
            //여기서 에러가 터지니까 return해서 나가짐
            //근데 생각한 에러가 안나버리면 하단의 가면 안되는 fail 단으로 가겠지
        }

        //2. 근데 try-catch문 없이 @Test 설정 주면 더간단해짐
        /**
         * memberService.join(member1);
         * memberService.join(member2); 이렇게만 적어도돼
         */

        //then
        fail("예외가 발생합니다."); //여기로 오면 안되는 예제를 fail로 지정
    }
}