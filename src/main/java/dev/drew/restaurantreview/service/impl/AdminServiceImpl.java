package dev.drew.restaurantreview.service.impl;

import dev.drew.restaurantreview.entity.AccountRequestEntity;
import dev.drew.restaurantreview.entity.ClaimEntity;
import dev.drew.restaurantreview.entity.RestaurantEntity;
import dev.drew.restaurantreview.entity.UserEntity;
import dev.drew.restaurantreview.exception.ClaimNotFoundException;
import dev.drew.restaurantreview.exception.InsufficientPermissionException;
import dev.drew.restaurantreview.mapper.AccountRequestMapper;
import dev.drew.restaurantreview.mapper.ClaimMapper;
import dev.drew.restaurantreview.repository.AccountRequestRepository;
import dev.drew.restaurantreview.repository.ClaimRepository;
import dev.drew.restaurantreview.repository.RestaurantRepository;
import dev.drew.restaurantreview.repository.UserRepository;
import dev.drew.restaurantreview.service.AdminService;
import dev.drew.restaurantreview.util.SecurityUtils;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.openapitools.model.AdminStatus;
import org.openapitools.model.ClaimStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static org.openapitools.model.User.RoleEnum.ADMIN;

@Slf4j
@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ClaimRepository claimRepository;

    @Autowired
    private AccountRequestRepository accountRequestRepository;

    @Autowired
    private ClaimMapper claimMapper;

    @Autowired
    private AccountRequestMapper accountRequestMapper;

    public AdminServiceImpl() {
    }

    @Override
    public List<ClaimStatus> getPendingClaims() {

        log.info("Starting: Get Pending Claims");

        // Check if the current user is an admin
        if (!SecurityUtils.isAdmin()) {
            throw new InsufficientPermissionException("User does not have permission to view admin page");
        }

        List<ClaimEntity> pendingClaimsEntities = claimRepository.findByStatus(ClaimEntity.ClaimStatus.PENDING);
        log.info("Pending Claims Incoming!");
        return pendingClaimsEntities.stream()
                .map(claimMapper::toClaim)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ClaimStatus acceptClaim(Long claimId) {

        log.info("Starting: Accept Claims");

        // Check if the current user is an admin
        if (!SecurityUtils.isAdmin()) {
            throw new InsufficientPermissionException("User does not have permission to view admin page");
        }

        log.info("Searching for claims");
        // Check claim exists
        ClaimEntity claim = claimRepository.findById(claimId)
                .orElseThrow(() -> new ClaimNotFoundException("Claim with id " + claimId + " not found"));

        // Update the Restaurant with owner id
        log.info("Updating Restaurant Owner");
        RestaurantEntity restaurant = claim.getRestaurant();
        restaurant.setOwner(claim.getClaimant());
        // Save the updated restaurant to the database
        restaurantRepository.save(restaurant);
        log.info("Restaurant Updated");

        // Update the claim
        log.info("Updating Claim");
        claim.setStatus(ClaimEntity.ClaimStatus.ACCEPTED);
        claim.setUpdatedAt(OffsetDateTime.now().toLocalDateTime());
        claimRepository.save(claim);
        log.info("Claim Updated");

        return claimMapper.toClaim(claim);
    }

    @Override
    @Transactional
    public ClaimStatus rejectClaim(Long claimId) {

        log.info("Starting: Reject Claims");

        // Check if the current user is an admin
        if (!SecurityUtils.isAdmin()) {
            throw new InsufficientPermissionException("User does not have permission to view admin page");
        }

        // Check claim exists
        log.info("Searching for claims");
        ClaimEntity claim = claimRepository.findById(claimId)
                .orElseThrow(() -> new ClaimNotFoundException("Claim with id " + claimId + " not found"));

        // Update the claim
        claim.setStatus(ClaimEntity.ClaimStatus.REJECTED);
        claim.setUpdatedAt(OffsetDateTime.now().toLocalDateTime());
        claimRepository.save(claim);

        return claimMapper.toClaim(claim);
    }

    @Override
    public List<AdminStatus> getPendingAdminAccounts() {

        log.info("Starting: Get Pending Admin Accounts");

        // Check if the current user is an admin
        if (!SecurityUtils.isAdmin()) {
            throw new InsufficientPermissionException("User does not have permission to view admin page");
        }

        log.info("Searching for admin claims");
        List<AccountRequestEntity> pendingAccountRequests = accountRequestRepository.findByStatus(AccountRequestEntity.Status.PENDING);
        log.info("Admin claims incoming!");
        return pendingAccountRequests.stream()
                .map(accountRequestMapper::toAdminStatus)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public AdminStatus acceptAdminAccount(Long requestId) {

        log.info("Starting: Accept Admin Account");

        // Check if the current user is an admin
        if (!SecurityUtils.isAdmin()) {
            throw new InsufficientPermissionException("User does not have permission to view admin page");
        }

        // Check request exists
        log.info("Searching for admin claim");
        AccountRequestEntity request = accountRequestRepository.findById(requestId)
                .orElseThrow(() -> new ClaimNotFoundException("Request with id " + requestId + " not found"));
        log.info("Admin Claim Found");

        // Update the user
        log.info("Updating The User");
        UserEntity user = request.getUser();
        user.setRole(ADMIN);
        userRepository.save(user);
        log.info("User Updated");


        // Update the claim
        log.info("Updating The Claim");
        request.setStatus(AccountRequestEntity.Status.ACCEPTED);
        request.setUpdatedAt(OffsetDateTime.now().toLocalDateTime());
        accountRequestRepository.save(request);
        log.info("Claim Updated");
        return accountRequestMapper.toAdminStatus(request);
    }

    @Override
    @Transactional
    public AdminStatus rejectAdminAccount(Long requestId) {

        log.info("Starting: Reject Admin Account");

        // Check if the current user is an admin
        if (!SecurityUtils.isAdmin()) {
            throw new InsufficientPermissionException("User does not have permission to view admin page");
        }

        // Check request exists
        log.info("Searching for admin claim");
        AccountRequestEntity request = accountRequestRepository.findById(requestId)
                .orElseThrow(() -> new ClaimNotFoundException("Request with id " + requestId + " not found"));
        log.info("Admin Claim Found");

        // Update the claim
        log.info("Updating The Claim");
        request.setStatus(AccountRequestEntity.Status.REJECTED);
        request.setUpdatedAt(OffsetDateTime.now().toLocalDateTime());
        accountRequestRepository.save(request);
        log.info("Claim Updated");
        return accountRequestMapper.toAdminStatus(request);
    }
}
