package pg.example;

import pg.example.User;

import java.util.List;

/**
 * Created by olesya.daderko on 11/5/18.
 */
public interface CRUDService {

    List<User> getUsers();
    void saveUser(User user);
    User updateUser(User user);
    boolean deleteUser(long userId);
}
