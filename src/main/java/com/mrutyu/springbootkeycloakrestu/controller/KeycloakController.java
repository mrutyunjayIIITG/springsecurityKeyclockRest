package com.mrutyu.springbootkeycloakrestu.controller;

import com.mrutyu.springbootkeycloakrestu.dto.User;
import com.mrutyu.springbootkeycloakrestu.security.KeyCloakSecurityUtil;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@SecurityRequirement(name = "Keycloak")
public class KeycloakController {

    @Autowired
    private KeyCloakSecurityUtil keyCloakSecurityUtil;

//    @PostMapping("/signup")
//    public ResponseEntity<String> signUp(@RequestBody User user) {
//        try {
//            Keycloak keycloak = keyCloakSecurityUtil.getKeycloakInstance();
//            RealmResource realmResource = keycloak.realm(keyCloakSecurityUtil.getRealm());
//            UsersResource usersResource = realmResource.users();
//
//            UserRepresentation userRepresentation = new UserRepresentation();
//            userRepresentation.setUsername(user.getUsername());
//            userRepresentation.setEmail(user.getEmail());
//            userRepresentation.setFirstName(user.getFirstName());
//            userRepresentation.setLastName(user.getLastName());
//            userRepresentation.setEnabled(true); // Enable the user
//
//            // Set password
//            CredentialRepresentation credential = new CredentialRepresentation();
//            credential.setType(CredentialRepresentation.PASSWORD);
//            credential.setValue(user.getPassword());
//            credential.setTemporary(false);
//            userRepresentation.setCredentials(Collections.singletonList(credential));
//
//            // Create the user
//            usersResource.create(userRepresentation);
//
//            return ResponseEntity.status(HttpStatus.CREATED).body("User created successfully");
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create user: " + e.getMessage());
//        }
//    }

}
