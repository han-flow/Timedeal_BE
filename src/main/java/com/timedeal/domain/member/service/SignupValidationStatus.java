package com.timedeal.domain.member.service;


import com.timedeal.domain.member.dto.SignupDTO;
import com.timedeal.domain.member.repository.MemberRepository;
import com.timedeal.domain.member.util.PasswordManager;
import com.timedeal.global.dto.BaseResponseStatus;
import com.timedeal.global.exception.custom.InvalidUserException;

enum SignupValidationStatus {

	PASSWORD_INVALID {
		@Override
		public void validate(SignupDTO signupDto, MemberRepository memberRepository, PasswordManager passwordManager) {
			if (!passwordManager.isValid(signupDto.getMemberPassword())) {
				throw new InvalidUserException(BaseResponseStatus.USER_PASSWORD_INVALID);
			}
		}
	},

	USER_ALREADY_EXISTS {
		@Override
		public void validate(SignupDTO signupDto, MemberRepository memberRepository, PasswordManager passwordManager) {
			if (memberRepository.findByMemberEmail(signupDto.getMemberEmail()).isPresent()) {
				throw new InvalidUserException(BaseResponseStatus.USER_ALREADY_EXISTS);
			}
		}
	};

	abstract void validate(SignupDTO signupDto, MemberRepository memberRepository, PasswordManager passwordManager);

	public static void validateAll(SignupDTO signupDto, MemberRepository memberRepository, PasswordManager passwordManager) {
		for (SignupValidationStatus status : SignupValidationStatus.values()) {
			status.validate(signupDto, memberRepository, passwordManager);
		}
	}
}