package com.basic.backend.common.config.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class BaseResult<T> {

	@ApiModelProperty("결과 코드")
	private String code;

	private T contents;

	@ApiModelProperty("결과 메시지")
	private String message;

	@ApiModelProperty("개발자 확인 메시지")
	private String developerMessage;

	public BaseResult(T t, BaseResultCode baseResultCode) {
		this.contents = t;
		this.setBaseResult(baseResultCode);
	}
	
	public void setBaseResult(BaseResultCode baseResultCode) {
		this.code = baseResultCode.name(); // todo name?
		this.message = baseResultCode.getMessage();
		this.developerMessage = baseResultCode.getDeveloperMessage();
	}

}