package practice.wfh.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import practice.wfh.entities.UsersEntity;
import practice.wfh.repositories.UsersRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UsersController {

    private final UsersRepository usersRepository;

    /**As  of Spring 4.3, classes with a single constructor can omit the @Autowired annotation. A nice little bit of
     * convenience and boilerplate removal!
     * ALSO SEE http://olivergierke.de/2013/11/why-field-injection-is-evil/
     */
    @Autowired
    public UsersController(UsersRepository usersRepository) {
        Assert.notNull(usersRepository, "MyCollaborator must not be null!");
        this.usersRepository = usersRepository;
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsersEntity> updateUser(@PathVariable("id") String id, @RequestBody UsersEntity tutorial) {
        Optional<UsersEntity> userData = usersRepository.findById(id);

        if (userData.isPresent()) {
            UsersEntity _user = userData.get();
            _user.setFirstName(tutorial.getLastName());
            _user.setLastName(tutorial.getFirstName());

            return new ResponseEntity<>(usersRepository.save(_user), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @GetMapping
    public ResponseEntity<List<UsersEntity>> getAllUsers() {
        List<UsersEntity> users = new ArrayList<>();

        usersRepository.findAll().forEach((users::add));


        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping(path="/{userId}")
    public ResponseEntity<UsersEntity> getUser(@PathVariable String userId){

        Optional<UsersEntity> userData = usersRepository.findById(userId);

        System.out.println(userData);

        if (userData.isPresent()) {
            return new ResponseEntity<>(userData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @PostMapping("/createUser")
    public ResponseEntity<UsersEntity> createUser(@RequestBody UsersEntity user) {
        try {
            UsersEntity _user = usersRepository.save(new UsersEntity(user.getFirstName(), user.getLastName()));
            return new ResponseEntity<>(_user, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable("id") String id) {
        try {
            usersRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }

}
