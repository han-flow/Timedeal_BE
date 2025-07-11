package com.timedeal.global.security.service;

import com.timedeal.domain.member.entity.Member;
import com.timedeal.domain.member.repository.MemberRepository;
import com.timedeal.global.dto.BaseResponseStatus;
import com.timedeal.global.exception.custom.InvalidAuthenticationException;
import com.timedeal.global.security.model.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String memberEmail) throws UsernameNotFoundException {
        Member member = memberRepository.findByMemberEmail(memberEmail).
                orElseThrow(() -> new InvalidAuthenticationException(BaseResponseStatus.USER_NOT_FOUND));

        return new CustomUserDetails(member.getId(), member.getPassword(), member.getRole());
    }
}
