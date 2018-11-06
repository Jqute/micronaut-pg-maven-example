package pg.example;

import io.reactiverse.pgclient.PgPoolOptions;
import io.reactiverse.reactivex.pgclient.*;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by olesya.daderko on 11/5/18.
 */
@Singleton
public class CRUDServiceImpl implements CRUDService {

    private PgPool client;

    public CRUDServiceImpl() {
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
    public void saveUser(User user) {
        client.preparedQuery("INSERT INTO users (id, username) VALUES ($1, $2)", Tuple.of(user.getId(), user.getUsername()),  ar -> {
            if (ar.succeeded()) {
                PgRowSet rows = ar.result();
                System.out.println(rows.rowCount());
            } else {
                System.out.println("Failure: " + ar.cause().getMessage());
            }
        });
    }

    @Override
    public User updateUser(User user) {
        return null;
    }

    @Override
    public boolean deleteUser(long userId) {
        return false;
    }
}
