package ma.skypay.service;

import ma.skypay.model.User;

import java.util.ArrayList;

public interface IUserService {
    void createOrUpdate(ArrayList<User> users, int userId, int balance);
    User find(ArrayList<User> users, int userId);
    void charge(User user, int amount);
}
