package com.dentalmanagementapp.security;

public class UserContext {
    private static final ThreadLocal<CurrentUser> currentUser = new ThreadLocal<>();

    public static void setCurrentUser(CurrentUser user) {
        currentUser.set(user);
    }

    public static CurrentUser getCurrentUser() {
        return currentUser.get();
    }

    public static void clear() {
        currentUser.remove();
    }

}
