package dev.drew.restaurantreview.service.impl;

import dev.drew.restaurantreview.entity.ClaimEntity;
import dev.drew.restaurantreview.entity.RestaurantEntity;
import dev.drew.restaurantreview.exception.ClaimNotFoundException;
import dev.drew.restaurantreview.exception.InsufficientPermissionException;
import dev.drew.restaurantreview.mapper.ClaimMapper;
import dev.drew.restaurantreview.repository.ClaimRepository;
import dev.drew.restaurantreview.repository.RestaurantRepository;
import dev.drew.restaurantreview.service.AdminService;
import jakarta.transaction.Transactional;
import org.openapitools.model.ClaimStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static dev.drew.restaurantreview.util.SecurityUtils.isAdmin;
import static dev.drew.restaurantreview.util.SecurityUtils.isAdminOrOwner;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private ClaimRepository claimRepository;

    @Autowired
    private ClaimMapper claimMapper;

    public AdminServiceImpl() {
    }

    @Override
    public List<ClaimStatus> getPendingClaims() {

        // Check if the current user is an admin
        if (!isAdmin()) {
            throw new InsufficientPermissionException("User does not have permission to view admin page");
        }

        List<ClaimEntity> pendingClaimsEntities = claimRepository.findByStatus(ClaimEntity.ClaimStatus.PENDING);
        return pendingClaimsEntities.stream()
                .map(claimMapper::toClaim)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ClaimStatus acceptClaim(Long claimId) {

        // Check if the current user is an admin
        if (!isAdmin()) {
            throw new InsufficientPermissionException("User does not have permission to view admin page");
        }

        // Check claim exists
        ClaimEntity claim = claimRepository.findById(claimId)
                .orElseThrow(() -> new ClaimNotFoundException("Claim with id " + claimId + " not found"));

        // Update the Restaurant with owner id
        RestaurantEntity restaurant = claim.getRestaurant();
        restaurant.setOwner(claim.getClaimant());
        // Save the updated restaurant to the database
        restaurantRepository.save(restaurant);

        // Update the claim
        claim.setStatus(ClaimEntity.ClaimStatus.ACCEPTED);
        claim.setUpdatedAt(OffsetDateTime.now().toLocalDateTime());
        claimRepository.save(claim);

        return claimMapper.toClaim(claim);
    }
}
