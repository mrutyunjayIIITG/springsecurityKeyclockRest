package com.mrutyu.springbootkeycloakrestu.controller;

import com.mrutyu.springbootkeycloakrestu.dto.UserRegistrationRequest;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.ws.rs.core.Response;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@SecurityRequirement(name = "Keycloak")
public class UserRegistrationController {

    @Value("${realm}")
    private String realm;

    @Value("${client-id}")
    private String clientId;

    @Value("${client-secret}")
    private String clientSecret;

    @Value("${name}")
    private String adminUsername;

    @Value("${password}")
    private String adminPassword;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserRegistrationRequest userRequest) {
        try {
            // Get access token
            String token = getToken(adminUsername, adminPassword, clientId, clientSecret);

            // Create Keycloak instance with token
            Keycloak keycloak = KeycloakBuilder.builder()
                    .serverUrl("https://key.enfyjobs.com/")
                    .realm(realm)
                    .clientId(clientId)
                    .clientSecret(clientSecret)
                    .username(adminUsername)
                    .password(adminPassword)
                    .build();

            // Create user representation
            UserRepresentation userRepresentation = new UserRepresentation();
            userRepresentation.setUsername(userRequest.getUsername());
            userRepresentation.setFirstName(userRequest.getFirstName());
            userRepresentation.setLastName(userRequest.getLastName());
            userRepresentation.setEmail(userRequest.getEmail());
            userRepresentation.setEnabled(true);

            CredentialRepresentation credential = new CredentialRepresentation();
            credential.setType(CredentialRepresentation.PASSWORD);
            credential.setValue(userRequest.getPassword());
            credential.setTemporary(false);
            userRepresentation.setCredentials(Collections.singletonList(credential));

            // Register user
            Response response = keycloak.realm(realm).users().create(userRepresentation);
            String userId = CreatedResponseUtil.getCreatedId(response);

            if (userId != null) {
                return new ResponseEntity<>("User registration successful", HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>("User registration failed", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private String getToken(String username, String password, String clientId, String clientSecret) throws Exception {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost("https://key.enfyjobs.com/realms/dive-dev/protocol/openid-connect/token");

        // Set form parameters
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("grant_type", "password"));
        params.add(new BasicNameValuePair("client_id", clientId));
        params.add(new BasicNameValuePair("client_secret", clientSecret));
        params.add(new BasicNameValuePair("username", username));
        params.add(new BasicNameValuePair("password", password));
        httpPost.setEntity(new UrlEncodedFormEntity(params));

        // Execute the request
        HttpResponse response = httpClient.execute(httpPost);
        HttpEntity entity = response.getEntity();
        String responseBody = EntityUtils.toString(entity);

        // Extract access token from response body
        // Note: You need to parse the JSON response to extract the access token
        // Here's just a simple example assuming the response body is JSON
        String accessToken = responseBody.substring(responseBody.indexOf("access_token\":\"") + 15);
        accessToken = accessToken.substring(0, accessToken.indexOf("\""));

        return accessToken;
    }
}
