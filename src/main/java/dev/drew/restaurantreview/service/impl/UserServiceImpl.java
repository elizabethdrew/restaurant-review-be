package dev.drew.restaurantreview.service.impl;

import dev.drew.restaurantreview.exception.InsufficientPermissionException;
import dev.drew.restaurantreview.exception.InvalidInputException;
import dev.drew.restaurantreview.exception.UserNotFoundException;
import dev.drew.restaurantreview.service.UserService;
import dev.drew.restaurantreview.util.SecurityUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import dev.drew.restaurantreview.entity.UserEntity;
import dev.drew.restaurantreview.mapper.UserMapper;
import dev.drew.restaurantreview.repository.UserRepository;
import org.openapitools.model.*;
import org.springframework.stereotype.Service;
import dev.drew.restaurantreview.util.interfaces.EntityUserIdProvider;
import org.passay.*;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final BCryptPasswordEncoder passwordEncoder;

    // Implement EntityUserIdProvider for UserEntity
    private final EntityUserIdProvider<UserEntity> userEntityUserIdProvider = UserEntity::getId;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }


    public User addNewUser(UserInput userInput) {

        // Check if username already exists
        if(userRepository.existsByUsername(userInput.getUsername())) {
            throw new InvalidInputException("Username already in use.");
        }

        // Check if email already exists (Assuming UserInput and UserEntity have an 'email' field)
        if(userRepository.existsByEmail(userInput.getEmail())) {
            throw new InvalidInputException("Email already in use.");
        }

        // Validate Password
        validatePassword(userInput.getPassword());

        UserEntity user = userMapper.toUserEntity(userInput);
        user.setCreatedAt(OffsetDateTime.now());
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        UserEntity savedUser = userRepository.save(user);

        User savedApiUser = userMapper.toUser(savedUser);

        return savedApiUser;
    }

    private void validatePassword(String password) {
        // Define rules
        List<Rule> rules = new ArrayList<>();
        rules.add(new LengthRule(8, 100)); // password length between 8 and 100
        rules.add(new CharacterRule(EnglishCharacterData.UpperCase, 1)); // at least 1 uppercase character
        rules.add(new CharacterRule(EnglishCharacterData.LowerCase, 1)); // at least 1 lowercase character
        rules.add(new CharacterRule(EnglishCharacterData.Digit, 1)); // at least 1 digit
        rules.add(new CharacterRule(EnglishCharacterData.Special, 1)); // at least 1 special character

        PasswordValidator validator = new PasswordValidator(rules);
        RuleResult result = validator.validate(new PasswordData(password));

        if (!result.isValid()) {
            String message = String.join(", ", validator.getMessages(result));
            throw new InvalidInputException("Invalid password: " + message);
        }
    }



    public User getUserById(Integer userId) {

        UserEntity userEntity = userRepository.findByIdAndIsDeletedFalse(userId.longValue())
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));

        if (!SecurityUtils.isAdminOrCreator(userEntity, userEntityUserIdProvider)) {
            throw new InsufficientPermissionException("User does not have permission view this profile");
        }

        return userMapper.toUser(userEntity);
    }



    public void deleteUserById(Integer userId) {
        // Retrieve the user with the specified ID from the repository
        UserEntity userEntity = userRepository.findByIdAndIsDeletedFalse(userId.longValue())
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));

        if (!SecurityUtils.isAdminOrCreator(userEntity, userEntityUserIdProvider)) {
            throw new InsufficientPermissionException("User does not have permission to update this profile");
        }

        // Mark the user as deleted
        userEntity.setIsDeleted(true);

        // Save the updated user entity
        userRepository.save(userEntity);
    }

    public User updateUserById(Integer userId, UserInput userInput)
            throws UserNotFoundException, InsufficientPermissionException {

        // Retrieve the user with the specified ID from the repository
        UserEntity userEntity = userRepository.findByIdAndIsDeletedFalse(userId.longValue())
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));

        if (!SecurityUtils.isAdminOrCreator(userEntity, userEntityUserIdProvider)) {
            throw new InsufficientPermissionException("User does not have permission to update this profile");
        }

        UserEntity updatedEntity = userMapper.toUserEntity(userInput);
        updatedEntity.setId(userEntity.getId());
        updatedEntity.setCreatedAt(userEntity.getCreatedAt());

        // Check if new password is provided
        if(userInput.getPassword() != null) {
            // Encrypt new password
            String encryptedPassword = passwordEncoder.encode(userInput.getPassword());
            updatedEntity.setPassword(encryptedPassword);
        } else {
            updatedEntity.setPassword(userEntity.getPassword());
        }

        UserEntity savedUser = userRepository.save(updatedEntity);
        User savedApiUser = userMapper.toUser(savedUser);

        return savedApiUser;
    }
}
