package com.sample.servlet.infrastructure.services;

import com.sample.servlet.dao.UserDao;
import com.sample.servlet.infrastructure.models.User;

import java.util.Optional;

public class UserService {
    private final UserDao userDao;

    public UserService(UserDao userDao){
        this.userDao = userDao;
    }

    public UserService() {
        this.userDao = new UserDao();
    }

    public void addUser(String login, String password, String email) {
        if (login != null && !login.isEmpty() && email != null && !email.isEmpty() && password != null && !password.isEmpty()) {
            User user = new User(login, password, email);
            if (userDao.save(user)) return;
        }
        throw new IllegalArgumentException();
    }


    public boolean validUser(String login, String password) {
        Optional<User> user = userDao.get(login);

        return user.isPresent() && user.get().getPass().equals(password);
    }
}
