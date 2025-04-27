package com.example.courseregistration.utils;


public class LoggedInUser {
    private static long userId;

    public static long getUserId() {
        return userId;
    }

    public static void setUserId(long id) {
        userId = id;
    }
}
