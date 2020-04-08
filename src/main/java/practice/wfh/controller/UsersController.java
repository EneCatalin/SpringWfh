package practice.wfh.controller;

import org.hibernate.action.internal.EntityActionVetoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import practice.wfh.entity.UsersEntity;
import practice.wfh.model.UserModel;
import practice.wfh.service.UsersService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UsersController {

    private final UsersService usersService;

    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }


    //TODO MAKE THIS RETURN A MODEL ARRAY
    @GetMapping
    public ResponseEntity<List<UsersEntity>> getAllUsers() {
        List<UsersEntity> users = usersService.getAllUsers();

        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping(path = "/{userId}")
    public ResponseEntity<UserModel> getUser(@PathVariable String userId) throws EntityActionVetoException {
        UserModel userModel = usersService.getUserById(userId);

        return new ResponseEntity<UserModel>(userModel, HttpStatus.OK);

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


//
//    @PostMapping("/createUser")
//    public ResponseEntity<UsersEntity> createUser(@RequestBody UsersEntity user) {
//        try {
//            UsersEntity _user = usersRepository.save(new UsersEntity(user.getFirstName(), user.getLastName()));
//            return new ResponseEntity<>(_user, HttpStatus.CREATED);
//        } catch (Exception e) {
//            return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
//        }
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<HttpStatus> deleteUser(@PathVariable("id") String id) {
//        try {
//            usersRepository.deleteById(id);
//            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//        } catch (Exception e) {
//            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
//        }
//    }

}
