package ru.kontr.auth_service.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kontr.auth_service.model.RegistrationRequest;
import ru.kontr.auth_service.model.RegistrationResult;
import ru.kontr.auth_service.service.RegistrationService;

@RestController
@RequestMapping("/registration")
public class RegistrationController {

    private final RegistrationService registrationService;

    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @PostMapping
    public RegistrationResult registerUser(RegistrationRequest request) {
        return registrationService.registerUser(request);
    }
}
