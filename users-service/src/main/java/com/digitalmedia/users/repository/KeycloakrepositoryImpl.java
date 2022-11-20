package com.digitalmedia.users.repository;

import com.digitalmedia.users.model.User;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.GroupRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Repository
public class KeycloakrepositoryImpl implements IKeycloakRepository {

    @Autowired
    private Keycloak keycloakClient;
    @Value("${dm.keycloak.realm}")
    private String realm;

    @Override
    public User findByName(String name) {
        List<UserRepresentation> userRepresentation = keycloakClient.realm(realm)
                .users()
                .search(name);

        if (!userRepresentation.isEmpty()){
            UserRepresentation userResponseMap = userRepresentation
                    .stream()
                    .findFirst()
                    .orElse(new UserRepresentation());
            return toUser(userResponseMap);
        }
        return new User("fe8433bf-b6fc-483c", "Juan10", "juam@email.com", "Juan", "Valencia");
    }

    @Override
    public Optional<User> findById(String id) {
        UserRepresentation ur = keycloakClient
                .realm(realm)
                .users()
                .get(id)
                .toRepresentation();
        return Optional.of(toUser(ur));
    }

    @Override
    public List<User> getAllNoAdmin() {
        List<UserRepresentation> keycloakusersList = keycloakClient.realm(realm).users().list();

        List<UserRepresentation> usersNoAdmin = keycloakusersList.stream()
                .filter(ur -> {
                    List<GroupRepresentation> groupsUser = keycloakClient.realm(realm)
                            .users()
                            .get(ur.getId())
                            .groups();
                    return groupsUser.stream().noneMatch(grupo -> Objects.equals(grupo.getName(), "admin"));
                }).collect(Collectors.toList());

        return usersNoAdmin.stream().map(user -> toUser(user)).collect(Collectors.toList());
    }

    @Override
    public Optional<User> updateNationality(String id, String nationality) {
        UserResource userResource = keycloakClient
                .realm(realm)
                .users()
                .get(id);
        UserRepresentation ur = userResource.toRepresentation();

        Map<String, List<String>> attributes = new HashMap<>();
        attributes.put("nacionality", List.of(nationality));
        ur.setAttributes(attributes);

        userResource.update(ur);
        return Optional.of(toUser(ur));
    }

    private User toUser(UserRepresentation userRepresentation) {
        return new User(userRepresentation.getId(), userRepresentation.getUsername(),
                userRepresentation.getEmail(), userRepresentation.getFirstName(), userRepresentation.getLastName());
    }
}
