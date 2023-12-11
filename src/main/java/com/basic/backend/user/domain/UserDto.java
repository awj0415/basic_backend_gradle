package com.basic.backend.user.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class UserDto {

    @ApiModel(value = "사용자 등록 요청")
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Getter
    @Setter
    public static class UserAddReq {

        @ApiModelProperty(value="아이디", example = "reali23538", required = true)
        private String userId;

        @ApiModelProperty(value="패스워드", required = true)
        private String password;

        @ApiModelProperty(value="이름", example = "홍길동", required = true)
        private String name;

        @ApiModelProperty(value="휴대폰", example = "111-2222-3333", required = true)
        private String phone;

        @ApiModelProperty(value="이메일", example = "reali23538@gmail.com", required = true)
        private String email;

    }

    @ApiModel(value = "사용자 등록 응답")
    @Builder
    @Getter
    @Setter
    public static class UserAddRes {
        @ApiModelProperty(value="유저seq", example = "1", required = true)
        private Long userSeq;

        @ApiModelProperty(value="아이디", example = "reali23538", required = true)
        private String userId;

        @ApiModelProperty(value="패스워", required = true)
        private String password;

    }

    @Builder
    @Getter
    @Setter
    public static class  UserRes {

        private String userId;

        private String name;

        private String phone;

        private String email;

//        private Date createDate;

        public String toString() {
            return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
        }

    }

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UserUdtReq {

        private String userId;

        private String password;

        private String name;

        private String phone;

        private String email;

    }

}
