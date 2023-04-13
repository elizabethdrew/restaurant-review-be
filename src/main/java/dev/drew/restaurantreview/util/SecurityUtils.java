package dev.drew.restaurantreview.util;

import dev.drew.restaurantreview.entity.SecurityUser;
import dev.drew.restaurantreview.entity.UserEntity;
import dev.drew.restaurantreview.util.interfaces.EntityUserIdProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {

    public static Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SecurityUser securityUser = (SecurityUser) authentication.getPrincipal();
        UserEntity userEntity = securityUser.getUserEntity();
        return userEntity.getId();
    }

    public static boolean isAdmin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SecurityUser securityUser = (SecurityUser) authentication.getPrincipal();
        return securityUser.hasRole("ROLE_ADMIN");
    }

    public static <T> boolean isAdminOrOwner(T entity, EntityUserIdProvider<T> userIdProvider) {
        return isAdmin() || getCurrentUserId().equals(userIdProvider.getUserId(entity));
    }
}