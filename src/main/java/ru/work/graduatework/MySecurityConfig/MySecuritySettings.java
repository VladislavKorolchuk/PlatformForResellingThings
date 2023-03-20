package ru.work.graduatework.MySecurityConfig;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.work.graduatework.Entity.Ads;
import ru.work.graduatework.Entity.Comment;
import ru.work.graduatework.dto.Role;

public class MySecuritySettings {
    public MySecuritySettings() {
    }

    public static MyUserUtils getUserDetailsFromContext() {
        return (MyUserUtils) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public static long getUserIdFromContext() {
        return getUserDetailsFromContext().getId();
    }
    public static void checkPermissionEditingAds(Ads ads) {
        MyUserUtils userDetails = getUserDetailsFromContext();
        if (!userDetails.getAuthorities().contains(Role.ADMIN) && userDetails.getId() != ads.getAuthor().getId()) {
            throw new AccessDeniedException("Чтобы редактировать объявление нужны права ROLE_ADMIN, или быть его автором");
        }
    }

    public static void checkPermissionEditingComment(Comment comment) {
        MyUserUtils userDetails = getUserDetailsFromContext();
        if (!userDetails.getAuthorities().contains(Role.ADMIN) && userDetails.getId() != comment.getAuthor().getId()) {
            throw new AccessDeniedException("Чтобы редактировать комментарий нужны права ROLE_ADMIN, или быть его автором");
        }
    }
}
