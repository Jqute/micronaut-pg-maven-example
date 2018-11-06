package pg.example;

import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import io.micronaut.http.HttpStatus;
import pg.example.model.User;
import pg.example.service.UserServiceImpl;

import javax.inject.Inject;
import java.util.List;

@Controller("/users")
public class UserController {

    private final UserServiceImpl crudService;

    @Inject
    public UserController(UserServiceImpl crudService) {
        this.crudService = crudService;
    }

    @Get("/")
    @Produces(MediaType.APPLICATION_JSON)
    public List<User> users() {
        return crudService.getUsers();
    }

    @Get("/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public User user(long userId) {
        return crudService.getUser(userId);
    }

    @Post("/")
    @Consumes(MediaType.APPLICATION_JSON)
    public HttpStatus save(@Body User user) {
        return crudService.saveUser(user) ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
    }

    @Delete("/{userId}")
    public HttpStatus delete(long userId) {
        return crudService.deleteUser(userId) ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
    }
}