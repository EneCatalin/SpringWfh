package practice.wfh.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import practice.wfh.localExceptions.UserNotFoundException;
import practice.wfh.model.UserModel;
import practice.wfh.service.UsersService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UsersController {

    private final UsersService usersService;

    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @GetMapping
    public ResponseEntity<List<UserModel>> getAllUsers() {
        List<UserModel> users = usersService.getAllUsers();

        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping(path = "/{userId}")
    public ResponseEntity<UserModel> getUser(@PathVariable String userId) throws UserNotFoundException {
        UserModel userModel = usersService.getUserById(userId);

        return new ResponseEntity<>(userModel, HttpStatus.OK);

    }

    @ExceptionHandler({UserNotFoundException.class})
    @PostMapping("/createUser")
    public ResponseEntity<UserModel> createUser(@RequestBody @Valid UserModel user) throws Exception {
        System.out.println("111222");
        UserModel userModel = usersService.createUser(user);

        return new ResponseEntity<>(userModel, HttpStatus.OK);
    }


//    @PutMapping("/{id}")
//    public ResponseEntity<UsersEntity> updateUser(@PathVariable("id") String id, @RequestBody UsersEntity tutorial) {
//        Optional<UsersEntity> userData = usersRepository.findById(id);
//
//        if (userData.isPresent()) {
//            UsersEntity _user = userData.get();
//            _user.setFirstName(tutorial.getLastName());
//            _user.setLastName(tutorial.getFirstName());
//
//            return new ResponseEntity<>(usersRepository.save(_user), HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }


    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable("id") String id) throws Exception {

        return new ResponseEntity<>(usersService.deleteUserEntity(id));

    }

}
