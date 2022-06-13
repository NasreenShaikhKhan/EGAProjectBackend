package bankingsystem.backend.dao;

import bankingsystem.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  //  User findByAccountNo(String accountNo);

}
