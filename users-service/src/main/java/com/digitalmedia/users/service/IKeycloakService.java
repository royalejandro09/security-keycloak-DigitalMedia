package com.digitalmedia.users.service;

import com.digitalmedia.users.model.User;
import com.digitalmedia.users.model.dto.UserResponse;

import java.util.List;

public interface IKeycloakService {

    User findByfirstName(String firstName);
    User findById(String id);
    List<UserResponse> getAllNoAdmin();
    User updateNationality(String id, String nationality);

}
