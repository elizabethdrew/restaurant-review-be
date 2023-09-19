package dev.drew.restaurantreview.controller;

import dev.drew.restaurantreview.service.AdminService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.openapitools.api.AdminApi;
import org.openapitools.model.ClaimStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/claims")
public class AdminController implements AdminApi {

    @Autowired
    private AdminService adminService;

    @GetMapping("/pending")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<List<ClaimStatus>> getPendingClaims() {
        List<ClaimStatus> pendingClaims = adminService.getPendingClaims();
        return ResponseEntity.ok(pendingClaims);
    }
}
