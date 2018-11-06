package pg.example.service;

import io.reactiverse.pgclient.PgPoolOptions;
import io.reactiverse.reactivex.pgclient.PgClient;
import io.reactiverse.reactivex.pgclient.PgIterator;
import io.reactiverse.reactivex.pgclient.PgPool;
import io.reactiverse.reactivex.pgclient.PgRowSet;
import io.reactiverse.reactivex.pgclient.Row;
import io.reactiverse.reactivex.pgclient.Tuple;
import pg.example.model.User;

import javax.inject.Singleton;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by olesya.daderko on 11/5/18.
 */
@Singleton
public class UserServiceImpl implements UserService {

    private PgPool client;

    public UserServiceImpl() {
        PgPoolOptions options = new PgPoolOptions()
                .setPort(5432)
                .setHost("localhost")
                .setDatabase("postgres")
                .setUser("postgres")
                .setPassword("example")
                .setMaxSize(5);
        client = PgClient.pool(options);
    }

    @Override
    public List<User> getUsers() {
        List<User> users = new LinkedList<>();
        PgIterator pgIterator = client.rxPreparedQuery("SELECT * from users").blockingGet().iterator();
        while (pgIterator.hasNext()) {
            Row row = pgIterator.next();

            users.add(new User(row.getLong("id"), row.getString("username")));
        }
        return users;
    }

    @Override
    public User getUser(long userId) {
        List<User> users = new LinkedList<>();
        PgIterator pgIterator = client.rxPreparedQuery("SELECT * FROM users WHERE id=$1", Tuple.of((int) userId))
                .blockingGet().iterator();
        while (pgIterator.hasNext()) {
            Row row = pgIterator.next();

            users.add(new User(row.getLong("id"), row.getString("username")));
        }
        return users.iterator().next();
    }

    @Override
    public boolean saveUser(User user) {
        String sqlQuery = "INSERT INTO users (id, username) VALUES ($1, $2) ON CONFLICT (id) DO UPDATE SET username = $3";
        client.preparedQuery(sqlQuery, Tuple.of((int) user.getId(), user.getUsername(), user.getUsername()), ar -> {
            if (ar.succeeded()) {
                PgRowSet rows = ar.result();
                System.out.println(rows.rowCount());
            } else {
                System.out.println("Failure: " + ar.cause().getMessage());
            }
        });
        return true;
    }

    @Override
    public boolean deleteUser(long userId) {
        client.preparedQuery("DELETE FROM users WHERE id=$1", Tuple.of((int) userId),  ar -> {
            if (ar.succeeded()) {
                PgRowSet rows = ar.result();
                System.out.println(rows.rowCount());
            } else {
                System.out.println("Failure: " + ar.cause().getMessage());
            }
        });
        return true;
    }
}
