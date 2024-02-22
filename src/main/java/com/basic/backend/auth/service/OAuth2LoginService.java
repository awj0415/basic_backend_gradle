package com.basic.backend.auth.service;

import com.basic.backend.auth.domain.OAuthDto;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class OAuth2LoginService {

    private final Environment env;
    private final RestTemplate restTemplate = new RestTemplate();

    public void login(String authorizationCode, String oAuthCompany) {
        String accessToken = getAccessToken(authorizationCode, oAuthCompany);
        OAuthDto.UserInfo userInfo = getUserInfo(accessToken, oAuthCompany);
        System.out.println("userInfo >>> " + userInfo.toString());

        // todo membership registration or login processing >>> create token
    }

    private String getAccessToken(String authorizationCode, String oAuthCompany) {
        String clientId = env.getProperty("oauth2." + oAuthCompany + ".client-id");
        String clientSecret = env.getProperty("oauth2." + oAuthCompany + ".client-secret");
        String redirectUri = env.getProperty("oauth2." + oAuthCompany + ".redirect-uri");
        String authServerUri = env.getProperty("oauth2." + oAuthCompany + ".authorization-server-uri");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("code", authorizationCode);
        params.add("client_id", clientId);
        params.add("client_secret", clientSecret);
        params.add("redirect_uri", redirectUri);
        params.add("grant_type", "authorization_code");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity httpEntity = new HttpEntity(params, headers);

        ResponseEntity<JsonNode> responseNode = restTemplate.exchange(authServerUri, HttpMethod.POST, httpEntity, JsonNode.class);
        JsonNode accessTokenNode = responseNode.getBody();
        System.out.println("accessTokenNode >>> " + accessTokenNode);
        return accessTokenNode.get("access_token").asText();
    }

    private OAuthDto.UserInfo getUserInfo(String accessToken, String oAuthCompany) {
        String resourceServerUri = env.getProperty("oauth2." + oAuthCompany + ".resource-server-uri");

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        HttpEntity httpEntity = new HttpEntity(headers);

        ResponseEntity<JsonNode> responseNode = restTemplate.exchange(resourceServerUri, HttpMethod.GET, httpEntity, JsonNode.class);
        JsonNode userResourceNode = responseNode.getBody();
        System.out.println("userResourceNode >>> " + userResourceNode);

        return OAuthDto.UserInfo.builder()
                .id(userResourceNode.get("id").asText())
                .email(userResourceNode.get("email").asText())
                .nickName(userResourceNode.get("name").asText())
                .build();
    }

}
