package com.amorepacific.sampleapp.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class CommonException extends RuntimeException {

	private static final long serialVersionUID = 2091972500749549823L;

	@Getter
	private String code;

	@Getter
	private String message;

}
