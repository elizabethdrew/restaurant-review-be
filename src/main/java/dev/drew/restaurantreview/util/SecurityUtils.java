package dev.drew.restaurantreview.util;

import dev.drew.restaurantreview.model.SecurityUser;
import dev.drew.restaurantreview.entity.UserEntity;
import dev.drew.restaurantreview.util.interfaces.EntityOwnerIdProvider;
import dev.drew.restaurantreview.util.interfaces.EntityUserIdProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Slf4j
public class SecurityUtils {

    public static void setCurrentUser(SecurityUser user) {
        Authentication auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    public static Long getCurrentUserId() {
        log.info("Authenticating User...");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken)) {
            SecurityUser securityUser = (SecurityUser) authentication.getPrincipal();
            UserEntity userEntity = securityUser.getUserEntity();
            log.info("User Authenticated");
            return userEntity.getId();
        }
        log.info("User Not Authorised");
        return null;
    }

    public static UserEntity getCurrentUser() {
        log.info("Authenticating User...");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken)) {
            SecurityUser securityUser = (SecurityUser) authentication.getPrincipal();
            log.info("User Authenticated");
            return securityUser.getUserEntity();
        }
        log.info("User Not Authorised");
        return null;
    }

    public static boolean isAdmin() {
        log.info("Checking User Authorisations...");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SecurityUser securityUser = (SecurityUser) authentication.getPrincipal();
        return securityUser.hasRole("ROLE_ADMIN");
    }

    public static <T> boolean isAdminOrOwner(T entity, EntityOwnerIdProvider<T> ownerIdProvider) {
        log.info("Checking User Authorisations...");
        return isAdmin() || getCurrentUserId().equals(ownerIdProvider.getOwnerId(entity));
    }

    public static <T> boolean isAdminOrCreator(T entity, EntityUserIdProvider<T> userIdProvider) {
        log.info("Checking User Authorisations...");
        return isAdmin() || getCurrentUserId().equals(userIdProvider.getUserId(entity));
    }
}