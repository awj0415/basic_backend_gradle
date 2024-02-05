package com.basic.backend.common.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

public class CommonDto {

    @ApiModel(value = "file upload response")
    @AllArgsConstructor
    @Getter
    public static class FileUploadRes {
        @ApiModelProperty(value="file url", example = "https://xxx.s3.ap-northeast-2.amazonaws.com/public/xxx.png")
        private String fileUrl;
    }

}
