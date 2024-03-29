package dev.drew.restaurantreview.service;

import org.openapitools.model.AdminStatus;
import org.openapitools.model.ClaimStatus;

import java.util.List;

public interface AdminService {
    List<ClaimStatus> getPendingClaims();

    ClaimStatus acceptClaim(Long claimId);

    ClaimStatus rejectClaim(Long claimId);

    List<AdminStatus> getPendingAdminAccounts();

    AdminStatus acceptAdminAccount(Long requestId);

    AdminStatus rejectAdminAccount(Long requestId);
}
