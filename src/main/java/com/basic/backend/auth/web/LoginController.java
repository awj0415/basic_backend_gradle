package com.basic.backend.auth.web;

import com.basic.backend.auth.service.OAuth2LoginService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "Login API")
@RestController
@RequiredArgsConstructor
public class LoginController {

    private final OAuth2LoginService oAuth2LoginService;

    @GetMapping("/auth/{oAuthCompany}")
    public String oAuth2Login(@RequestParam(value = "code") String authorizationCode,
                              @PathVariable String oAuthCompany) {
        oAuth2LoginService.login(authorizationCode, oAuthCompany);
        return "SUCCESS";
    }

}
