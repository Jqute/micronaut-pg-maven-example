package pg.example.service;

import pg.example.model.User;

import java.util.List;

/**
 * Created by olesya.daderko on 11/5/18.
 */
public interface UserService {

    List<User> getUsers();
    User getUser(long userId);
    boolean saveUser(User user);
    boolean deleteUser(long userId);
}
