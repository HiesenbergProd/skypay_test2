package ma.skypay.service;

import ma.skypay.exception.InsufficientBalanceException;
import ma.skypay.exception.UserNotFoundException;
import ma.skypay.model.User;

import java.util.ArrayList;
import java.util.Optional;

public class UserService implements IUserService {
    @Override
    public void createOrUpdate(ArrayList<User> users, int userId, int balance)
    {
        if (users == null)
            throw new IllegalArgumentException("users arrayList can not be null");
        if (userId <= 0)
            throw new IllegalArgumentException("invalid userId");

        if (balance < 0)
            throw new IllegalArgumentException("Invalid balance");


        Optional<User> result = users.stream()
                .filter(user -> user.getId() == userId)
                .findFirst();

        if (result.isPresent())
        {
            result.get().setBalance(balance);
        }
        else
        {
            users.add(new User(userId, balance));
        }
    }

    @Override
    public User find(ArrayList<User> users, int userId)
    {
        return users.stream()
                .filter(user -> user.getId() == userId)
                .findFirst()
                .orElseThrow(() -> new UserNotFoundException(userId));
    }

    @Override
    public void charge(User user, int amount)
    {
        if (user == null)
            throw new IllegalArgumentException("user can not be null");

        if (amount <= 0)
            throw  new IllegalArgumentException("invalid amount");

        if (user.getBalance() < amount)
            throw new InsufficientBalanceException(user.getId());

        user.setBalance(user.getBalance() - amount);
    }
}
