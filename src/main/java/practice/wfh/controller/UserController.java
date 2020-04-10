package practice.wfh.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import practice.wfh.localExceptions.UserNotFoundException;
import practice.wfh.model.UserModel;
import practice.wfh.service.UsersService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UsersService usersService;

    public UserController(UsersService usersService) {
        this.usersService = usersService;
    }

    //TODO THROW ResponseStatusException IN SERVICES ?

    @GetMapping
    public ResponseEntity<List<UserModel>> getAllUsers() {
        List<UserModel> users = usersService.getAllUsers();

        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping(path = "/{userId}")
    public ResponseEntity<UserModel> getUser(@PathVariable String userId) throws ResponseStatusException {
        UserModel userModel = usersService.getUserModel(userId);

        return new ResponseEntity<>(userModel, HttpStatus.OK);

    }

    @ExceptionHandler({ResponseStatusException.class})
    @PostMapping("/createUser")
    public ResponseEntity<UserModel> createUser(@RequestBody @Valid UserModel user) throws Exception {

        return new ResponseEntity<>(usersService.createUser(user), HttpStatus.OK);
    }


    @PutMapping("/{id}")
    public ResponseEntity<UserModel> updateUser(@PathVariable("id") String userId,
                                                @RequestBody @Valid UserModel userModel) throws UserNotFoundException {
        return new ResponseEntity<UserModel>(usersService.updateUserModel(userModel,userId),HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable("id") String id) throws Exception {

        return new ResponseEntity<>(usersService.deleteUserEntity(id));

    }

}
