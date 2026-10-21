package ru.kontr.auth_service.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class RegistrationRequest {
    private String login;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
}
