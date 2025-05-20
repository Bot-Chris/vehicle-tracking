// File: src/main/java/com/logistics/dto/JwtResponse.java
package com.logistics.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
public class JwtResponse {
    private String token;
    private UUID id;
    private String username;
    private String email;
    private List<String> roles;
}