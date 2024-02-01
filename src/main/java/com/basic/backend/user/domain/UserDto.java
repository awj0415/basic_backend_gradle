package com.basic.backend.user.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

public class UserDto {

    @ApiModel(value = "사용자 등록 요청")
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Getter
    @Setter
    public static class UserAddReq {

        @NotBlank(message = "ID is required.")
        @Size(min = 8, max = 20, message = "Please enter your ID between 8 and 20 characters.")
        @ApiModelProperty(value="ID", example = "reali23538", required = true)
        private String userId;

        @NotBlank(message = "Password is required.")
        @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}",
                message = "The password should be 8 to 20 characters long " +
                        "and contain upper and lower case letters, numbers, and special characters.")
        @ApiModelProperty(value="password", required = true)
        private String password;

        @NotBlank(message = "Name is required.")
        @ApiModelProperty(value="name", example = "James", required = true)
        private String name;

        @NotBlank(message = "Phone is required.")
        @ApiModelProperty(value="phone", example = "111-2222-3333", required = true)
        private String phone;

        @NotBlank(message = "Email is required.")
//        @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,6}$",
//                message = "The email format is incorrect.")
        @Email(message = "The email format is incorrect.")
        @ApiModelProperty(value="email", example = "reali23538@gmail.com", required = true)
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

        @ApiModelProperty(value="이름", example = "홍길동", required = true)
        private String name;

        @ApiModelProperty(value="휴대폰", example = "111-2222-3333")
        private String phone;

        @ApiModelProperty(value="이메일", example = "reali@reali.com")
        private String email;
    }

    @Getter
    @Setter
    public static class GetUsersReq {
        private Integer currentPage;

        private String searchText;
    }

    @Getter
    @Setter
    @Builder
    public static class GetUsersRes {

        private List<User> users;

        private Long totalCount;

        private Integer rowCntPerPage;

        @Getter
        @Setter
        @Builder
        public static class User {

            private Long userSeq;

            private String userId;

            private String name;

            private String phone;

            private String email;

        }
    }

    @Builder
    @Getter
    @Setter
    public static class UserRes {

        private Long userSeq;

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

//        private String password;

        private String name;

        private String phone;

        private String email;

    }

}
