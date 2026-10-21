package ru.kontr.auth_service.service;

import ru.kontr.auth_service.model.RegistrationRequest;
import ru.kontr.auth_service.model.RegistrationResult;

public interface RegistrationService {
    RegistrationResult registerUser(RegistrationRequest request);
}
