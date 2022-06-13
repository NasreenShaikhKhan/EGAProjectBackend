package bankingsystem.backend.dao;

import bankingsystem.backend.entity.Account;
import bankingsystem.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, String> {

    Account findByAccountNo(String accountNo);

}
