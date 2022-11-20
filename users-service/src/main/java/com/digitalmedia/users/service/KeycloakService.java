package com.digitalmedia.users.service;

import com.digitalmedia.users.model.User;
import com.digitalmedia.users.model.dto.UserResponse;
import com.digitalmedia.users.repository.IKeycloakRepository;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class KeycloakService implements IKeycloakService {

    private IKeycloakRepository keycloakRepository;

    public KeycloakService(IKeycloakRepository keycloakRepository) {
        this.keycloakRepository = keycloakRepository;
    }


    @Override
    public User findByfirstName(String userName) {
        return keycloakRepository.findByName(userName);
    }

    @Override
    public User findById(String id) {
        return keycloakRepository.findById(id).get();
    }

    @Override
    public List<UserResponse> getAllNoAdmin() {
        List<User> usersNoAdmin =  keycloakRepository.getAllNoAdmin();
        return usersNoAdmin.stream()
                .map(user -> toUserResponse(user))
                .collect(Collectors.toList());
    }

    @Override
    public User updateNationality(String id, String nationality) {
        return keycloakRepository.updateNationality(id, nationality).get();
    }

    private UserResponse toUserResponse(User user) {
        return new UserResponse(user.getUsername(), user.getEmail());
    }
}
