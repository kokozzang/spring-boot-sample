package com.kokozzang.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@AllArgsConstructor
public abstract class CommonException extends RuntimeException {

	private static final long serialVersionUID = 2091972500749549823L;

	@Getter
	@NonNull
	protected String code;

	@Getter
	@NonNull
	protected String message;

	@Getter
	private String data;

}
