package practice.wfh.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import practice.wfh.entity.UsersEntity;
import practice.wfh.localExceptions.UserNotFoundException;
import practice.wfh.model.UserModel;
import practice.wfh.repository.UsersRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UsersService {

    private final UsersRepository usersRepository;

    /**
     * As  of Spring 4.3, classes with a single constructor can omit the @Autowired annotation. A nice little bit of
     * convenience and boilerplate removal!
     * ALSO SEE http://olivergierke.de/2013/11/why-field-injection-is-evil/
     */
    @Autowired
    public UsersService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    private UsersEntity getUserEntityById(String userId) throws UserNotFoundException {
        Optional<UsersEntity> userEntity = usersRepository.findById(userId);

        if (userEntity.isPresent()) {
            return userEntity.get();
        } else {
            throw new UserNotFoundException();
        }
    }

    public List<UserModel> getAllUsers() {

        List<UserModel> usersModel = new ArrayList<>();
        List<UsersEntity> users = new ArrayList<>(usersRepository.findAll());

        users.forEach(userEntity -> usersModel.add(
                (new UserModel(userEntity.getId(), userEntity.getFirstName(), userEntity.getLastName())))
        );

        return usersModel;
    }

        public UserModel createUser(UserModel userModel) throws Exception {

            try {
                UsersEntity userEntity = usersRepository.save(
                        new UsersEntity(
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
    public UserModel getUserById(String userId) throws UserNotFoundException {

            UsersEntity userEntity = this.getUserEntityById(userId);

            return new UserModel(userEntity.getId(), userEntity.getFirstName(), userEntity.getLastName());

    }


    //This method looks all kinds of wrong for some reason
    //TODO find the right way to insert exceptions ?
    public HttpStatus deleteUserEntity(String userId) throws Exception {
        Optional<UsersEntity> optionalUsersEntity = usersRepository.findById(userId);

        try {
            usersRepository.deleteById(userId);
            return HttpStatus.NO_CONTENT;
        } catch (Exception e) {
            return HttpStatus.EXPECTATION_FAILED;
        }

    }

    //TODO Look over this method
    public UserModel updateUserModel(UserModel receivedModel, String userId) throws UserNotFoundException {
        Optional<UsersEntity> userData = usersRepository.findById(userId);

        if (userData.isPresent()) {
            UsersEntity userEntity = userData.get();
            userEntity.setFirstName(receivedModel.getLastName());
            userEntity.setLastName(receivedModel.getFirstName());
            usersRepository.save(userEntity);

            return new UserModel(userEntity.getId(), userEntity.getFirstName(), userEntity.getLastName());
        } else {
            throw new UserNotFoundException();
        }
    }

}
