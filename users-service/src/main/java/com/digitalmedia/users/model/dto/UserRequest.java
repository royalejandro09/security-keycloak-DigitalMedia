package com.digitalmedia.users.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UserRequest {

    @Schema(example = "avatar")
    private String id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
}
