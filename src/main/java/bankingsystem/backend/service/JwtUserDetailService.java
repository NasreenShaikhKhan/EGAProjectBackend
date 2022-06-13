package bankingsystem.backend.service;

import bankingsystem.backend.dao.AccountRepository;
import bankingsystem.backend.dao.UserRepository;
import bankingsystem.backend.entity.Account;
import bankingsystem.backend.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service("userDetailService")
@ComponentScan(basePackages = "com.test.project.security.model")
public class JwtUserDetailService implements UserDetailsService {

	@Autowired
	private AccountRepository accountRepository;

	@Override
	public UserDetails loadUserByUsername(String accountNo) throws UsernameNotFoundException {
		try {
			Account account = accountRepository.findByAccountNo(accountNo);
			if (account == null) {
				throw new UsernameNotFoundException("User not found  " + accountNo);
			}
			return new org.springframework.security.core.userdetails.User(account.getAccountNo(), account.getUser().getPin(), new ArrayList<>());
		} catch (Exception e){
			throw new UsernameNotFoundException("User not found  " + accountNo);
		}
	}

}
