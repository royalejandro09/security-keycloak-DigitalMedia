package com.digitalmedia.users.controller;

import com.digitalmedia.users.model.User;
import com.digitalmedia.users.model.dto.UserRequest;
import com.digitalmedia.users.model.dto.UserResponse;
import com.digitalmedia.users.service.IKeycloakService;
import com.digitalmedia.users.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    private final IUserService userService;
    private final IKeycloakService keycloak;

    public UserController(IUserService userService, IKeycloakService keycloak) {
        this.userService = userService;
        this.keycloak = keycloak;
    }

    @GetMapping("/admin")
    @PreAuthorize("hasAuthority('GROUP_admin')")
    public List<UserResponse> getAllNoAdmin() {
        return keycloak.getAllNoAdmin();
    }

    @GetMapping("/admin/username")
    public User finsByUserName(@RequestParam String username) {
        return keycloak.findByfirstName(username);
    }

    @GetMapping("/admin/id/{id}")
    @PreAuthorize("hasAuthority('GROUP_admin')")
    public User finsById(@PathVariable String id) {
        return keycloak.findById(id);
    }

    @PutMapping("/update")
    @PreAuthorize("hasAuthority('GROUP_admin')")
    public User updateUser(@RequestParam String id, @RequestParam String nationality) {
        return keycloak.updateNationality(id, nationality);
    }

    @GetMapping("/me")
    public User getUserExtra(Principal principal) {
        return userService.validateAndGetUserExtra(principal.getName());
    }

    @PostMapping("/me")
    public User saveUserExtra(@RequestBody UserRequest updateUserRequest, Principal principal) {
        Optional<User> userOptional = userService.getUserExtra(principal.getName());
        User userExtra = userOptional.orElseGet(() -> new User(principal.getName()));
        return userService.saveUserExtra(userExtra);
    }
}
