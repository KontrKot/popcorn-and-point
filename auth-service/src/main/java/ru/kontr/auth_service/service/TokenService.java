package ru.kontr.auth_service.service;

public interface TokenService {
    String generateToken(String login, String firstName, String lastName, String email);
    void validateToken(String token);
}
