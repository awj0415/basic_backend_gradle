package com.basic.backend.auth.domain;

import lombok.Builder;
import lombok.Getter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class OAuthDto {

    @Builder
    @Getter
    public static class UserInfo {

        private String id;

        private String email;

        private String nickName;

        public String toString() {
            return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
        }

    }

}
