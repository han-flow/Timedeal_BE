package com.timedeal.domain.member.util;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PasswordManager {

	private final PasswordValidation passwordValidation;

	public boolean isValid(String password) {
		return passwordValidation.isValid(password);
	}
}