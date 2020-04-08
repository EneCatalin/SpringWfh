package practice.wfh.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import practice.wfh.entities.UsersEntity;

@Repository
public interface UsersRepository extends CrudRepository<UsersEntity, String> {
}
