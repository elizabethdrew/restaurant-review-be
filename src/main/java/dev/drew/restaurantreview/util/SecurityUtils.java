package dev.drew.restaurantreview.util;

import dev.drew.restaurantreview.model.SecurityUser;
import dev.drew.restaurantreview.entity.UserEntity;
import dev.drew.restaurantreview.util.interfaces.EntityUserIdProvider;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {

    public static void setCurrentUser(SecurityUser user) {
        Authentication auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    public static Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken)) {
            SecurityUser securityUser = (SecurityUser) authentication.getPrincipal();
            UserEntity userEntity = securityUser.getUserEntity();
            return userEntity.getId();
        }
        return null;
    }

    public static UserEntity getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken)) {
            SecurityUser securityUser = (SecurityUser) authentication.getPrincipal();
            return securityUser.getUserEntity();
        }
        return null;
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