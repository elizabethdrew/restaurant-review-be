package dev.drew.restaurantreview.controller;

import dev.drew.restaurantreview.service.AdminService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.openapitools.api.AdminApi;
import org.openapitools.model.AdminStatus;
import org.openapitools.model.ClaimStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController implements AdminApi {

    @Autowired
    private AdminService adminService;

    @GetMapping("/claims/pending")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<List<ClaimStatus>> getPendingClaims() {
        List<ClaimStatus> pendingClaims = adminService.getPendingClaims();
        return ResponseEntity.ok(pendingClaims);
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping("/claims/{claimId}/accept")
    public ResponseEntity<ClaimStatus> acceptClaim(@PathVariable("claimId") Long claimId) {
        ClaimStatus claimStatus = adminService.acceptClaim(claimId);
        return ResponseEntity.ok(claimStatus);
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping("/claims/{claimId}/reject")
    public ResponseEntity<ClaimStatus> rejectClaim(@PathVariable("claimId") Long claimId) {
        ClaimStatus claimStatus = adminService.rejectClaim(claimId);
        return ResponseEntity.ok(claimStatus);
    }

    @GetMapping("/users/pending")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<List<AdminStatus>> getPendingAdminAccounts() {
        List<AdminStatus> pendingAdminAccounts = adminService.getPendingAdminAccounts();
        return ResponseEntity.ok(pendingAdminAccounts);
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping("/users/{requestId}/accept")
    public ResponseEntity<AdminStatus> acceptAdminAccount(Long requestId) {
        AdminStatus adminStatus = adminService.acceptAdminAccount(requestId);
        return ResponseEntity.ok(adminStatus);
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping("/users/{requestId}/reject")
    public ResponseEntity<AdminStatus> rejectAdminAccount(Long requestId) {
        AdminStatus adminStatus = adminService.rejectAdminAccount(requestId);
        return ResponseEntity.ok(adminStatus);
    }
}
