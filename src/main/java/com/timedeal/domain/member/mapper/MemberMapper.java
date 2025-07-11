package com.timedeal.domain.member.mapper;

import com.timedeal.domain.member.dto.SignupDTO;
import com.timedeal.domain.member.entity.Member;
import com.timedeal.domain.member.entity.authenum.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberMapper {

    // 자체 회원가입 시 Member 변환
    public Member ofSignupDTO(SignupDTO signupDTO) {
        return Member.builder()
                .memberName(signupDTO.getMemberName())
                .memberEmail(signupDTO.getMemberEmail())
                .password(signupDTO.getMemberPassword())
                .role(Role.ROLE_USER)
                .memberAddress(signupDTO.getMemberAddress())
                .memberAddressDetail(signupDTO.getMemberAddressDetail())
                .build();
    }
}
