package com.basic.backend.user.domain;

import lombok.*;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class UserDto {

    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Getter
    @Setter
    public static class UserAddReq {

        private String userId;

        private String password;

        private String name;

        private String phone;

        private String email;

    }

    @Builder
    @Getter
    @Setter
    public static class UserAddRes {

        private Long userSeq;

        private String userId;

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
