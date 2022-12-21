package com.toy.shop.service;

import com.toy.shop.domain.Member;
import com.toy.shop.dto.MemberResponseDto;
import com.toy.shop.dto.MemberSaveRequestDto;
import com.toy.shop.dto.MemberUpdateRequestDto;
import com.toy.shop.exception.DataNotFoundException;
import com.toy.shop.repository.MemberJpaRepository;
import com.toy.shop.repository.MemberSpringJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.toy.shop.common.ResultCode.MEMBER_NOT_FOUND;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberJpaRepository memberRepository;
//    private final MemberSpringJpaRepository memberRepository;

    @Override
    @Transactional
    public MemberResponseDto save(MemberSaveRequestDto requestDto) {
        Member member = Member.createMember(requestDto);

        memberRepository.save(member);

        return new MemberResponseDto(member);
    }

    @Override
    public List<MemberResponseDto> findAll(String searchWord) {
        List<Member> members = memberRepository.findAll(searchWord);

        return members.stream()
                .map(MemberResponseDto::new)
                .toList();
    }

    @Override
    public MemberResponseDto findById(Long id) {
        Member member = memberRepository.findById(id).orElseThrow(() -> new DataNotFoundException(MEMBER_NOT_FOUND));

        return new MemberResponseDto(member);
    }

    @Override
    @Transactional
    public MemberResponseDto update(Long id, MemberUpdateRequestDto requestDto) {
        Member member = memberRepository.findById(id).orElseThrow(() -> new DataNotFoundException(MEMBER_NOT_FOUND));

        member.updateMember(requestDto);

        return new MemberResponseDto(member);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Member member = memberRepository.findById(id).orElseThrow(() -> new DataNotFoundException(MEMBER_NOT_FOUND));

        memberRepository.delete(member);
    }
}
