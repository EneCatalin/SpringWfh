package practice.wfh.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import practice.wfh.entity.UsersEntity;

@Repository
public interface UsersRepository extends JpaRepository<UsersEntity, String> {
}
