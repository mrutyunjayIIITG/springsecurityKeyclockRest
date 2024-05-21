package com.mrutyu.springbootkeycloakrestu.security;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component

public class KeyCloakSecurityUtil {

    private Keycloak keycloak; //this will create instamce of keyclok

    @Value("${server-url}")
    private String serverUrl;

    @Value("${realm}")
    private String realm;

    @Value("${grant-type}")
    private String grantType;

    @Value("${client-id}")
    private String clientId;

    @Value("${password}")
    private String password;

    @Value("${name}")
    private String username;

    public Keycloak getKeycloakInstance() {
        if (keycloak == null) {
            keycloak = KeycloakBuilder.builder()
                    .serverUrl(serverUrl)
                    .realm(realm)
                    .grantType(grantType)
                    .clientId(clientId)
                    .username(username)  // Updated to match the configuration file key 'name'
                    .password(password)
                    .build();
        }
        return keycloak;
    }

    public String getAccessToken() {
        return getKeycloakInstance().tokenManager().getAccessToken().getToken();
    }
//
//    public String getRealm() {
//        return realm;
//    }
}
