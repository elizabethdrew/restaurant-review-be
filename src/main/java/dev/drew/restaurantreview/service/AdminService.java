package dev.drew.restaurantreview.service;

import org.openapitools.model.ClaimStatus;

import java.util.List;

public interface AdminService {
    List<ClaimStatus> getPendingClaims();

    ClaimStatus acceptClaim(Long claimId);
}
