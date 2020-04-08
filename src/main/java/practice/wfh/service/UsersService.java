package practice.wfh.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import practice.wfh.entity.UsersEntity;
import practice.wfh.model.UserModel;
import practice.wfh.repository.UsersRepository;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UsersService {

    private final UsersRepository usersRepository;

    /**As  of Spring 4.3, classes with a single constructor can omit the @Autowired annotation. A nice little bit of
     * convenience and boilerplate removal!
     * ALSO SEE http://olivergierke.de/2013/11/why-field-injection-is-evil/
     */
    @Autowired
    public UsersService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    //TODO MAKE THIS RETURN A MODEL ARRAY
    public List<UsersEntity> getAllUsers() {
        List<UsersEntity> users = new ArrayList<>();

        usersRepository.findAll().forEach((users::add));

        return users;
    }

    public UserModel getUserById(String userId) throws EntityNotFoundException
    {
        Optional<UsersEntity> optionalUsersEntity = usersRepository.findById(userId);

        if(optionalUsersEntity.isPresent()) {
            UsersEntity userEntity = optionalUsersEntity.get();

            return new UserModel(userEntity.getId(),userEntity.getFirstName(),userEntity.getLastName());
        } else {
            throw new EntityNotFoundException("No employee record exist for given id");
        }
    }

}
