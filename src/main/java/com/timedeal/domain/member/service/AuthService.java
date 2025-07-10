package com.timedeal.domain.member.service;


import com.timedeal.domain.member.dto.SignupDTO;
import com.timedeal.domain.member.entity.Member;
import com.timedeal.domain.member.mapper.MemberMapper;
import com.timedeal.domain.member.repository.MemberRepository;
import com.timedeal.domain.member.util.PasswordManager;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;
    private final MemberMapper memberMapper;
    private final PasswordManager passwordManager;
    private final BCryptPasswordEncoder passwordEncoder;


    @Transactional
    public void signup(SignupDTO signupDTO) {
        SignupValidationStatus.validateAll(signupDTO, memberRepository, passwordManager);
        createUser(signupDTO);
    }

    private void createUser(SignupDTO signupDTO) {
        Member member = memberMapper.ofSignupDTO(signupDTO);
        member.encodePassword(passwordEncoder);
        memberRepository.save(member);
    }
}