package com.msbills.service;

import com.msbills.configs.FeignInterceptor;
import com.msbills.configs.OAuthFeignConfig;
import com.msbills.models.UserResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

// OAuthFeingConfig.class : para el flujo donde user-service busca el token de acceso en keycloak.
// FeignInterceptor.class : para el flujo donde user-service usa el token de acceso que viene de gateway en el filtro.

@FeignClient(name = "users-service", url = "http://localhost:8088/", configuration = OAuthFeignConfig.class)
public interface UsersFeignClient {

    @RequestMapping(method = RequestMethod.GET, value = "/users/admin/username")
    Optional<UserResponseDto> findByUserName(@RequestParam String username);
}
