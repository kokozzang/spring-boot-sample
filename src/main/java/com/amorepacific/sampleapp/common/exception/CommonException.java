package com.amorepacific.sampleapp.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class CommonException extends RuntimeException {

	private static final long serialVersionUID = 2091972500749549823L;

	@Getter
	protected String code;

	@Getter
	protected String message;

}
