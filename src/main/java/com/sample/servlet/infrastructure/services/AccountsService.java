package com.sample.servlet.infrastructure.services;

import com.sample.servlet.infrastructure.models.UserProfile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AccountsService {
    private static Map<String, UserProfile> loginToProfile;
    static {
        loginToProfile = new HashMap<>();
        loginToProfile.put("admin", new UserProfile("admin","123","f@mail.ru"));
    }
    private static Map<String, UserProfile> sessionIdToProfile= new HashMap<>();;

    public static void addNewUser(UserProfile userProfile) {
        loginToProfile.put(userProfile.getLogin(), userProfile);
    }

    public static UserProfile getUserByLogin(String login) {
        return loginToProfile.get(login);
    }
}
