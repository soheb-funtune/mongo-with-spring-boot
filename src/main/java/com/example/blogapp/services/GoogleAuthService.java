package com.example.blogapp.services;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GoogleAuthService {

    public static final String GOOGLE_AUTH_URL_USER_INFO_ENDPOINT = "https://www.googleapis.com/oauth2/v3/userinfo";
    private final RestTemplate restTemplate;

    public GoogleAuthService() {
        this.restTemplate = new RestTemplate();
    }

    public Map<String, Object> authenticateGoogleToken(String accessToken) {
        try {

            if (accessToken == null || accessToken.isEmpty()) {
                throw new IllegalArgumentException(" GoogleAuthService Access token is null or empty .");
            }

            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(accessToken);
            HttpEntity<String> httpEntity = new HttpEntity<>("", headers);

            ResponseEntity<Map> response = restTemplate.exchange(GOOGLE_AUTH_URL_USER_INFO_ENDPOINT, HttpMethod.GET,
                    httpEntity,
                    Map.class);

            Map<String, Object> userInfo = response.getBody();

            if (userInfo != null) {

                String sessionToken = (String) userInfo.get("sub");
                Map<String, Object> result = new HashMap<>(userInfo);
                result.put("sessionToken", sessionToken);

                return result;

            } else {
                throw new RuntimeException("Invalid access token.");
            }

        } catch (Exception e) {
            throw new RuntimeException("Error verifying Google token", e);
        }
    }

}
