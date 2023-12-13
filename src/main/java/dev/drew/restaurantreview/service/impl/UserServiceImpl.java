package dev.drew.restaurantreview.service.impl;

import dev.drew.restaurantreview.entity.AccountRequestEntity;
import dev.drew.restaurantreview.exception.InsufficientPermissionException;
import dev.drew.restaurantreview.exception.InvalidInputException;
import dev.drew.restaurantreview.exception.UserNotFoundException;
import dev.drew.restaurantreview.repository.AccountRequestRepository;
import dev.drew.restaurantreview.service.FileStorageService;
import dev.drew.restaurantreview.service.UserService;
import dev.drew.restaurantreview.util.SecurityUtils;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import dev.drew.restaurantreview.entity.UserEntity;
import dev.drew.restaurantreview.mapper.UserMapper;
import dev.drew.restaurantreview.repository.UserRepository;
import org.openapitools.model.*;
import org.springframework.stereotype.Service;
import dev.drew.restaurantreview.util.interfaces.EntityUserIdProvider;
import org.passay.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final AccountRequestRepository accountRequestRepository;
    private final UserMapper userMapper;
    private final BCryptPasswordEncoder passwordEncoder;
    private final FileStorageService fileStorageService;
    private final EntityUserIdProvider<UserEntity> userEntityUserIdProvider = UserEntity::getId;

    public UserServiceImpl(UserRepository userRepository, AccountRequestRepository accountRequestRepository, UserMapper userMapper, BCryptPasswordEncoder passwordEncoder, FileStorageService fileStorageService) {
        this.userRepository = userRepository;
        this.accountRequestRepository = accountRequestRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.fileStorageService = fileStorageService;
    }

    @Transactional
    public User addNewUser(UserInput userInput) {

        log.info("Starting: Add New User");

        // Check if username already exists
        log.info("Checking Usernames");
        if(userRepository.existsByUsername(userInput.getUsername())) {
            log.info("Username Exists");
            throw new InvalidInputException("Username already in use.");
        }
        log.info("Username Available");

        // Check if email already exists (Assuming UserInput and UserEntity have an 'email' field)
        log.info("Checking Emails");
        if(userRepository.existsByEmail(userInput.getEmail())) {
            log.info("Email Exists");
            throw new InvalidInputException("Email already in use.");
        }
        log.info("Email Available");

        // Validate Password
        validatePassword(userInput.getPassword());

        // Create New User
        log.info("Creating User");
        UserEntity user = userMapper.toUserEntity(userInput);
        user.setCreatedAt(OffsetDateTime.now());
        user.setPassword(passwordEncoder.encode(userInput.getPassword()));
        user.setRole(User.RoleEnum.REVIEWER);

        UserEntity savedUser = userRepository.save(user);
        log.info("User Saved");

        // Check if user is requesting admin permissions
        log.info("Check If User Wants To Be Admin");
        if(userInput.getRole() == UserInput.RoleEnum.ADMIN) {
            log.info("Creating Admin Request");
            AccountRequestEntity newRequest = new AccountRequestEntity();
            newRequest.setUser(savedUser);
            newRequest.setStatus(AccountRequestEntity.Status.PENDING);
            newRequest.setCreatedAt(OffsetDateTime.now().toLocalDateTime());
            newRequest.setUpdatedAt(OffsetDateTime.now().toLocalDateTime());
            accountRequestRepository.save(newRequest);
            log.info("Admin Request Created");
        }

        User savedApiUser = userMapper.toUser(savedUser);

        return savedApiUser;
    }

    private void validatePassword(String password) {

        log.info("Starting: Validate Password");

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
            log.info("Password Not Valid");
            String message = String.join(", ", validator.getMessages(result));
            throw new InvalidInputException("Invalid password: " + message);
        }

        log.info("Password Valid");
    }



    public User getUserById(Integer userId) {

        log.info("Starting: Get User");

        log.info("Searching For User");
        UserEntity userEntity = userRepository.findByIdAndIsDeletedFalse(userId.longValue())
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));

        log.info("User Found");
        if (!SecurityUtils.isAdminOrCreator(userEntity, userEntityUserIdProvider)) {
            log.info("User Doesn't Have Permission To View Profile");
            throw new InsufficientPermissionException("User does not have permission view this profile");
        }

        return userMapper.toUser(userEntity);
    }



    public void deleteUserById(Integer userId) {

        log.info("Staring: Delete User");

        // Retrieve the user with the specified ID from the repository
        log.info("Searching For User");
        UserEntity userEntity = userRepository.findByIdAndIsDeletedFalse(userId.longValue())
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));

        log.info("User Found");
        if (!SecurityUtils.isAdminOrCreator(userEntity, userEntityUserIdProvider)) {
            log.info("User Doesn't Have Permission To Delete Profile");
            throw new InsufficientPermissionException("User does not have permission to update this profile");
        }

        // Mark the user as deleted
        log.info("Marking User Deleted");
        userEntity.setIsDeleted(true);

        // Save the updated user entity
        userRepository.save(userEntity);
        log.info("User Updated");
    }

    public User updateUserById(Integer userId, UserUpdateInput userInput)
            throws UserNotFoundException, InsufficientPermissionException {

        log.info("Staring: Update User");

        // Retrieve the user with the specified ID from the repository
        log.info("Searching For User");
        UserEntity userEntity = userRepository.findByIdAndIsDeletedFalse(userId.longValue())
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));

        if (!SecurityUtils.isAdminOrCreator(userEntity, userEntityUserIdProvider)) {
            log.info("User Doesn't Have Permission To Update Profile");
            throw new InsufficientPermissionException("User does not have permission to update this profile");
        }

        log.info("Updating User");
        UserEntity updatedEntity = userMapper.toUpdateUserEntity(userInput);
        updatedEntity.setId(userEntity.getId());
        updatedEntity.setCreatedAt(userEntity.getCreatedAt());
        updatedEntity.setRole(userEntity.getRole());

        // Check if user is requesting admin permissions
        log.info("Check If User Wants To Be Admin");
        if(userInput.getRole() == UserUpdateInput.RoleEnum.ADMIN && userEntity.getRole() != User.RoleEnum.ADMIN) {
            log.info("Creating Admin Request");
            AccountRequestEntity newRequest = new AccountRequestEntity();
            newRequest.setUser(userEntity);
            newRequest.setStatus(AccountRequestEntity.Status.PENDING);
            newRequest.setCreatedAt(OffsetDateTime.now().toLocalDateTime());
            newRequest.setUpdatedAt(OffsetDateTime.now().toLocalDateTime());
            accountRequestRepository.save(newRequest);
            log.info("Admin Request Created");
        }

        // Check if new password is provided
        log.info("Checking If New Password Provided");
        if(userInput.getPassword() != null) {
            // Encrypt new password
            log.info("Encrypting New Password");
            String encryptedPassword = passwordEncoder.encode(userInput.getPassword());
            updatedEntity.setPassword(encryptedPassword);
            log.info("Updating Password");
        } else {
            log.info("No New Password");
            updatedEntity.setPassword(userEntity.getPassword());
        }

        UserEntity savedUser = userRepository.save(updatedEntity);
        log.info("User Saved");
        User savedApiUser = userMapper.toUser(savedUser);

        return savedApiUser;
    }

    @Override
    public User uploadUserImage(Integer userId, MultipartFile file) {
        UserEntity userEntity = userRepository.findByIdAndIsDeletedFalse(Long.valueOf(userId))
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));

        if (!SecurityUtils.isAdminOrCreator(userEntity, userEntityUserIdProvider)) {
            log.info("User Doesn't Have Permission To Update Profile Picture");
            throw new InsufficientPermissionException("User does not have permission to update this profile picture");
        }

        log.info("Uploading Image");
        String fileUrl = fileStorageService.uploadFile("user-image", userId, file, "image-bucket");

        log.info("Updating User");
        userEntity.setProfileImageUrl(fileUrl);
        UserEntity savedUser = userRepository.save(userEntity);

        return userMapper.toUser(savedUser);
    }
}
