package dev.drew.restaurantreview.util;

import dev.drew.restaurantreview.entity.SecurityUser;
import dev.drew.restaurantreview.util.interfaces.EntityUserIdProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

public class SecurityUtils {

    public static SecurityUser getCurrentSecurityUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() instanceof SecurityUser) {
            return (SecurityUser) authentication.getPrincipal();
        } else if (authentication.getPrincipal() instanceof Jwt) {
            return SecurityUser.fromJwt((Jwt) authentication.getPrincipal());
        }
        return null;
    }

    public static Long getCurrentUserId() {
        SecurityUser securityUser = getCurrentSecurityUser();
        return securityUser != null ? securityUser.getId() : null;
    }

    public static boolean isAdmin() {
        SecurityUser securityUser = getCurrentSecurityUser();
        return securityUser != null && securityUser.hasRole("ROLE_ADMIN");
    }

    public static <T> boolean isAdminOrOwner(T entity, EntityUserIdProvider<T> userIdProvider) {
        return isAdmin() || (getCurrentUserId() != null && getCurrentUserId().equals(userIdProvider.getUserId(entity)));
    }
}