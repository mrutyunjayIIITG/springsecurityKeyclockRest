package com.mrutyu.springbootkeycloakrestu.security;


import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class KeyCloakSecurityUtil {

    Keycloak keycloak;

    @Value("${keycloak.auth-server-url}")
    private String serverUrl;

    @Value("${keycloak.realm}")
    private String realm;

    @Value("${keycloak.grant-type}")
    private String  grantType;
    @Value("${keycloak.client-id}")
    private String clientId;
    @Value("${keycloak.password}")
    private String password;

    public Keycloak getKeycloakInstance() {
        if (keycloak == null) {
            keycloak = KeycloakBuilder.builder()
                    .serverUrl(serverUrl)
                    .realm(realm)
                    .grantType(grantType)
                    .clientId(clientId)
                    .clientSecret(password)
                    .build();
        }
        return keycloak;
    }

    public String getRealm() {
        return realm;
    }
}
