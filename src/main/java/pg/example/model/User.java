package pg.example.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by olesya.daderko on 11/5/18.
 */
public class User {
    private long id;
    private String username;

    @JsonCreator
    public User(@JsonProperty("id") long id, @JsonProperty("username") String username) {
        this.id = id;
        this.username = username;
    }

    public long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }
}
