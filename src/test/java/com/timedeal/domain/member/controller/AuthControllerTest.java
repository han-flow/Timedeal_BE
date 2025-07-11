package com.timedeal.domain.member.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.timedeal.domain.member.dto.SignupDTO;
import com.timedeal.domain.member.repository.MemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper; // JSON 직렬화를 위해 필요

    @Autowired
    private MemberRepository memberRepository; // 회원가입 후 DB 확인을 위해 필요

    @AfterEach
    void tearDown() {
        // 각 테스트 후에 데이터 클린업
        memberRepository.deleteAll();
    }


    @Test
    @DisplayName("성공적인 회원가입 테스트")
    @Transactional
    void testSuccessfulSignup() throws Exception {
        // Given
        SignupDTO request = new SignupDTO("한지성", "han@example.com", "han12311", "경기도 오산시", "101호");
        String jsonRequest = objectMapper.writeValueAsString(request);

        // When
        mockMvc.perform(post("/api/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk()); // HTTP 200 OK 응답 확인

        // Then
        assertThat(memberRepository.findByMemberEmail("han@example.com")).isPresent();
    }

    @Test
    @DisplayName("중복 이메일로 회원가입 실패 테스트")
    @Transactional
    void testSignupWithDuplicateEmail() throws Exception {
        // Given
        // 첫 번째 회원가입 (성공)
        SignupDTO firstRequest = new SignupDTO("한지성", "han@example.com", "han123456", "경기도 오산시", "101호");
        mockMvc.perform(post("/api/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(firstRequest)))
                .andDo(print())
                .andExpect(status().isOk()); // 첫 번째 회원가입은 성공해야 함

        // 두 번째 회원가입 (동일 이메일 사용)
        SignupDTO secondRequest = new SignupDTO("손종수", "han@example.com", "test123433", "서울시", "777호");



        // When & Then
        mockMvc.perform(post("/api/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(secondRequest)))
                .andDo(print())
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.code").value(2101)) // 예외에 정의된 코드
                .andExpect(jsonPath("$.message").value("이미 가입된 이메일입니다. 다른 방법으로 로그인하세요."));
    }

    @Test
    @DisplayName("유효하지 않은 비밀번호로 회원가입 실패 테스트")
    @Transactional
    void testSignupWithInvalidPassword() throws Exception {
        // Given
        SignupDTO request =  new SignupDTO("손종수", "han@example.com", "test1", "서울시", "777호");
        String jsonRequest = objectMapper.writeValueAsString(request);

        // When & Then
        mockMvc.perform(post("/api/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isBadRequest()) // HTTP 400 Bad Request 예상
                .andExpect(jsonPath("$.message").value("입력 값이 유효하지 않습니다"));
    }

    @Test
    @DisplayName("필수 필드 누락으로 회원가입 실패 테스트 (이름 누락)")
    @Transactional
    void testSignupWithMissingRequiredField() throws Exception {
        // Given
        SignupDTO request = new SignupDTO("", "han@example.com", "test123433", "서울시", "777호");
        String jsonRequest = objectMapper.writeValueAsString(request);

        // When & Then
        mockMvc.perform(post("/api/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isBadRequest()) // HTTP 400 Bad Request 예상
                .andExpect(jsonPath("$.message").value("입력 값이 유효하지 않습니다"));
    }


}