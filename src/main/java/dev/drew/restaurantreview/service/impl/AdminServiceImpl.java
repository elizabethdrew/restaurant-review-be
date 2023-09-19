package dev.drew.restaurantreview.service.impl;

import dev.drew.restaurantreview.entity.ClaimEntity;
import dev.drew.restaurantreview.exception.InsufficientPermissionException;
import dev.drew.restaurantreview.mapper.ClaimMapper;
import dev.drew.restaurantreview.repository.ClaimRepository;
import dev.drew.restaurantreview.service.AdminService;
import org.openapitools.model.ClaimStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static dev.drew.restaurantreview.util.SecurityUtils.isAdmin;
import static dev.drew.restaurantreview.util.SecurityUtils.isAdminOrOwner;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private ClaimRepository claimRepository;

    @Autowired
    private ClaimMapper claimMapper;

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
}
