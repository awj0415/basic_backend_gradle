package com.basic.backend.common.config.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class BaseResponse {

	public static <T> ResponseEntity<BaseResult<T>> ok() {
		return getResponseEntity(null, BaseResultCode.SUCCESS);
	}

	public static <T> ResponseEntity<BaseResult<T>> ok(T t) {
		return getResponseEntity(t, BaseResultCode.SUCCESS);
	}

	public static <T> ResponseEntity<BaseResult<T>> getResponseEntity(
			T t, BaseResultCode resultCode) {
		BaseResult<T> result = new BaseResult<>(t, resultCode);
		
		// httpStatus 셋팅
		HttpStatus httpStatus = HttpStatus.OK;
		if ( BaseResultCode.SUCCESS_CREATE  == resultCode ) {
			httpStatus = HttpStatus.CREATED;
		} else if ( BaseResultCode.COMMON_INVALID_PARAMS == resultCode ) {
			httpStatus = HttpStatus.BAD_REQUEST;
		} else if ( BaseResultCode.COMMON_INTERNAL_SERVER_ERROR == resultCode ) {
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<>(result, httpStatus);
	}

}
