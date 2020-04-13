package practice.wfh.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import practice.wfh.entity.UserEntity;
import practice.wfh.localExceptions.UserNotFoundException;
import practice.wfh.model.UserModel;
import practice.wfh.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

        return userEntity.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found"));
    }

    public List<UserModel> getAllUsers() {

        return userRepository.findAll().stream().map(this::toModel).collect(Collectors.toList());
    }

    private UserModel toModel(UserEntity userEntity) {
        return new UserModel(userEntity.getId(), userEntity.getFirstName(), userEntity.getLastName());
    }

    public UserModel createUser(UserModel userModel) {
        return toModel(userRepository.save(fromModel(userModel)));
    }

    private UserEntity fromModel(UserModel model) {
        return new UserEntity(model.getFirstName(), model.getLastName());
    }

    //TODO Break away the getUserEntity part (with the throw error included ofc)
    public UserModel getUserModel(String userId) {
        return toModel(this.getUserEntity(userId));
    }

    public HttpStatus deleteUserEntity(String userId) {
        Optional<UserEntity> optionalUsersEntity = userRepository.findById(userId);

        if (optionalUsersEntity.isPresent()) {
            throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, "No user with that id to delete");
        }
        userRepository.deleteById(userId);
        return HttpStatus.NO_CONTENT; // Is this really the status you want?

    }

    public UserModel updateUserModel(UserModel receivedModel, String userId) throws UserNotFoundException {
        Optional<UserEntity> userData = userRepository.findById(userId);

        if (userData.isPresent()) {
            UserEntity userEntity = userData.get();
            updateEntity(receivedModel, userEntity);

            return toModel(userEntity);
        } else {
            throw new UserNotFoundException();
        }
    }

    private void updateEntity(UserModel receivedModel, UserEntity userEntity) {
        userEntity.setFirstName(receivedModel.getLastName());
        userEntity.setLastName(receivedModel.getFirstName());
        userRepository.save(userEntity);
    }

}
