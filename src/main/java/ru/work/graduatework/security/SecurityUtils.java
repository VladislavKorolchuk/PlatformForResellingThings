package ru.work.graduatework.security;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.work.graduatework.Entity.Ad;
import ru.work.graduatework.dto.Role;

public class SecurityUtils {

    public SecurityUtils() {
    }

    public static MyUserDetails getUserDetailsFromContext() {
        return (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public static long getUserIdFromContext() {
        return getUserDetailsFromContext().getId();
    }

    public static void checkPermissionToAds(Ad ad) {
        MyUserDetails userDetails = getUserDetailsFromContext();

        if (!userDetails.getAuthorities().contains(Role.ADMIN)
                && userDetails.getId() != ad.getAuthor().getId()) {
            throw new AccessDeniedException("No access rights only Owner or Admin");
        }
    }

}
