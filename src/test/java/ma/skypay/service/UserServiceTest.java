package ma.skypay.service;

import ma.skypay.exception.InsufficientBalanceException;
import ma.skypay.exception.UserNotFoundException;
import ma.skypay.model.User;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    private final UserService userService = new UserService();

    @Test
    void createNewUser_addsToList() {
        ArrayList<User> users = new ArrayList<>();

        userService.createOrUpdate(users, 1, 500);

        assertEquals(1, users.size());
        User u = users.getFirst();
        assertEquals(1, u.getId());
        assertEquals(500, u.getBalance());
    }

    @Test
    void updateExistingUser_updatesBalance() {
        ArrayList<User> users = new ArrayList<>();
        users.add(new User(1, 100));

        userService.createOrUpdate(users, 1, 900);

        assertEquals(1, users.size());
        assertEquals(900, users.getFirst().getBalance());
    }

    @Test
    void createOrUpdate_withInvalidArgs_throws() {
        assertThrows(IllegalArgumentException.class, () -> userService.createOrUpdate(null, 1, 100));
        assertThrows(IllegalArgumentException.class, () -> userService.createOrUpdate(new ArrayList<>(), 0, 100));
        assertThrows(IllegalArgumentException.class, () -> userService.createOrUpdate(new ArrayList<>(), -1, 100));
        assertThrows(IllegalArgumentException.class, () -> userService.createOrUpdate(new ArrayList<>(), 1, -1));
    }

    @Test
    void find_returnsUser_whenPresent() {
        ArrayList<User> users = new ArrayList<>();
        User expected = new User(7, 300);
        users.add(expected);

        User actual = userService.find(users, 7);
        assertSame(expected, actual);
    }

    @Test
    void find_throwsWhenMissing() {
        ArrayList<User> users = new ArrayList<>();
        users.add(new User(1, 10));
        assertThrows(UserNotFoundException.class, () -> userService.find(users, 2));
    }

    @Test
    void charge_withValidAmount_reducesBalance() {
        User u = new User(3, 500);
        userService.charge(u, 120);
        assertEquals(380, u.getBalance());
    }

    @Test
    void charge_withInvalidArgs_throws() {
        assertThrows(IllegalArgumentException.class, () -> userService.charge(null, 10));
        assertThrows(IllegalArgumentException.class, () -> userService.charge(new User(1, 100), 0));
        assertThrows(IllegalArgumentException.class, () -> userService.charge(new User(1, 100), -5));
    }

    @Test
    void charge_withInsufficientBalance_throws() {
        User u = new User(9, 50);
        assertThrows(InsufficientBalanceException.class, () -> userService.charge(u, 60));
    }
}
