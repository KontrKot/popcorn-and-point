package ru.kontr.auth_service.service.impl;

import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kontr.auth_service.entity.UserEntity;
import ru.kontr.auth_service.exceptions.UserAlreadyExists;
import ru.kontr.auth_service.mapper.UserMapper;
import ru.kontr.auth_service.model.RegistrationRequest;
import ru.kontr.auth_service.model.RegistrationResult;
import ru.kontr.auth_service.repository.UserRepository;
import ru.kontr.auth_service.service.RegistrationService;
import ru.kontr.auth_service.service.TokenService;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.SecureRandom;
import java.security.spec.KeySpec;

@Service
public class RegistrationServiceImpl implements RegistrationService {

    private final TokenService tokenService;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public RegistrationServiceImpl(TokenService tokenService, UserRepository userRepository, UserMapper userMapper) {
        this.tokenService = tokenService;
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    @Transactional
    public RegistrationResult registerUser(RegistrationRequest request) {
        if (userRepository.existsByUserName(request.getLogin())) {
            throw new UserAlreadyExists();
        }
        createAndSaveUser(request);

        String token = tokenService.generateToken(request.getLogin(), request.getFirstName(), request.getLastName(), request.getEmail());
        return new RegistrationResult(token);
    }

    private void createAndSaveUser(RegistrationRequest request) {
        UserEntity userEntity = userMapper.mapUser(request);
        userEntity.setPasswordHash(createPasswordHash(request.getPassword()));
        userRepository.save(userEntity);
    }

    @SneakyThrows
    private String createPasswordHash(String password) {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] hash = factory.generateSecret(spec).getEncoded();
        return new String(hash);
    }
}
