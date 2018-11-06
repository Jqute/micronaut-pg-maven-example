package pg.example;

import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Produces;

import javax.inject.Inject;
import java.util.List;

@Controller("/CRUD")
public class CRUDController {

    private final CRUDServiceImpl crudService;

    @Inject
    public CRUDController(CRUDServiceImpl crudService) {
        this.crudService = crudService;
    }

    @Get("/user.json")
    @Produces(MediaType.APPLICATION_JSON)
    public List<User> index() {
        return crudService.getUsers();
    }

    @Get("/save")
    @Produces(MediaType.APPLICATION_JSON)
    public HttpStatus save() {
        crudService.saveUser(new User(1L, "Vasya"));
        return HttpStatus.OK;
    }
}