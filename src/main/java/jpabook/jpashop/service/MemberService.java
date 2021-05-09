package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @AllArgsConstructor => 모든 필드의 생성자를 만들어준다.
 * @RequiredArgsConstructor => final로 설정된 필드만 생성자를 만들어준다.
 */

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    /**
     *  필드인젝션 < Setter인젝션 < 생성자 인젝션
     *  생성자 인젝션이 가장 좋다.
     *  생성자가 하나만 있을경우 @Autowired 어노테이션을 사용안해도 자동으로 적용해준다.
     *  필드는 final로 해주는것이 좋다. 만약에 생성자에서 값 세팅을 하지않으면 컴파일시점에 체크를 해줘서 좋다.
     */
//    public MemberService(MemberRepository memberRepository) {
//        this.memberRepository = memberRepository;
//    }

    // 회원가입
    @Transactional
    public Long join(Member member){
        validateDuplicateMember(member); // 중복 회원 조회
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        //Exception
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입ㄴ디ㅏ.");
        }
    }

    //회원 전체 조회
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    //한명의 회원 조회
    public Member findOne(Long memberId) {
        return memberRepository.findById(memberId).get();
    }


    /**
     * member를 리턴할수 있지만 그럴경우 업데이트를 하면서 멤버를 쿼리하것이 된다.
     * 커맨드와 쿼리를 철저하게 분리하는 정책 > CQRS(명령과 조회의 책임분리)
     * 리턴하지 않거나 id값 정도만 반환하는 해주는것이 좋다
     */
    @Transactional
    public void update(Long id, String name) {
        Member member = memberRepository.findById(id).get();
        member.setName(name);
    }
}
