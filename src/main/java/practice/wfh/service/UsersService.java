package practice.wfh.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import practice.wfh.entity.UserEntity;
import practice.wfh.localExceptions.UserNotFoundException;
import practice.wfh.model.UserModel;
import practice.wfh.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UsersService {

    private final UserRepository userRepository;

    /**
     * As  of Spring 4.3, classes with a single constructor can omit the @Autowired annotation. A nice little bit of
     * convenience and boilerplate removal!
     * ALSO SEE http://olivergierke.de/2013/11/why-field-injection-is-evil/
     */
    @Autowired
    public UsersService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private UserEntity getUserEntity(String userId) throws ResponseStatusException {
        Optional<UserEntity> userEntity = userRepository.findById(userId);

        if (userEntity.isPresent()) {
            return userEntity.get();
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Foo Not Found");
        }
    }

    public List<UserModel> getAllUsers() {

        List<UserModel> usersModel = new ArrayList<>();
        List<UserEntity> users = new ArrayList<>(userRepository.findAll());

        users.forEach(userEntity -> usersModel.add(
                (new UserModel(userEntity.getId(), userEntity.getFirstName(), userEntity.getLastName())))
        );

        return usersModel;
    }

    public UserModel createUser(UserModel userModel) throws Exception {

        try {
            UserEntity userEntity = userRepository.save(
                    new UserEntity(
                            userModel.getFirstName(),
                            userModel.getLastName()
                    )
            );

            return new UserModel(userEntity.getId(), userEntity.getFirstName(), userEntity.getLastName());
        } catch (Exception e) {
            throw new Exception();
        }

    }

    //TODO Break away the getUserEntity part (with the throw error included ofc)
    public UserModel getUserModel(String userId) throws ResponseStatusException {

        UserEntity userEntity = this.getUserEntity(userId);

        return new UserModel(userEntity.getId(), userEntity.getFirstName(), userEntity.getLastName());

    }


    //This method looks all kinds of wrong for some reason
    //TODO find the right way to insert exceptions ?
    public HttpStatus deleteUserEntity(String userId) throws Exception {
        Optional<UserEntity> optionalUsersEntity = userRepository.findById(userId);

        try {
            userRepository.deleteById(userId);
            return HttpStatus.NO_CONTENT;
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.EXPECTATION_FAILED, "No user with that id to delete");
        }

    }

    //TODO Look over this method
    public UserModel updateUserModel(UserModel receivedModel, String userId) throws UserNotFoundException {
        Optional<UserEntity> userData = userRepository.findById(userId);

        if (userData.isPresent()) {
            UserEntity userEntity = userData.get();
            userEntity.setFirstName(receivedModel.getLastName());
            userEntity.setLastName(receivedModel.getFirstName());
            userRepository.save(userEntity);

            return new UserModel(userEntity.getId(), userEntity.getFirstName(), userEntity.getLastName());
        } else {
            throw new UserNotFoundException();
        }
    }

}
