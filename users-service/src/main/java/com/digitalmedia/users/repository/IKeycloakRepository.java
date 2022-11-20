package com.digitalmedia.users.repository;

import com.digitalmedia.users.model.User;

import java.util.List;
import java.util.Optional;

public interface IKeycloakRepository {

    User findByName(String name);
    Optional<User> findById(String id);
    List<User> getAllNoAdmin();
    Optional<User> updateNationality(String id, String nationality);
}
