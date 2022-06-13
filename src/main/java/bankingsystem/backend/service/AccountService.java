package bankingsystem.backend.service;

import bankingsystem.backend.config.JwtTokenUtil;
import bankingsystem.backend.dao.ProfileRepository;
import bankingsystem.backend.dto.AccountDto;
import bankingsystem.backend.dto.Constants;
import bankingsystem.backend.entity.Account;
import bankingsystem.backend.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@Slf4j
public class AccountService {

    @Autowired
    private ProfileRepository accountRepository;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;



    private AccountService(ProfileRepository accountRepository,JwtTokenUtil jwtTokenUtil)
    {
       this.accountRepository=accountRepository;
       this.jwtTokenUtil=jwtTokenUtil;

    }



    public Account createAccount(User user,String accountNo) {
        Account account = new Account();
        account.setAccountNo(accountNo);
        account.setBalance((BigDecimal.valueOf(1000)));
        account.setUser(user);
        return accountRepository.save(account);


    }

    public AccountDto getAccountFromToken(String token) {
       String accountNo = jwtTokenUtil.getUsernameFromToken(token);
        Account account= accountRepository.findByAccountNo(accountNo);
        AccountDto accountDto=AccountDto.builder().balance(account.getBalance()).accountNumber(accountNo).build();
        return accountDto;
    }

    public Account getAccountByAccountNo(String accountNo) {
        return accountRepository.findByAccountNo(accountNo);
    }

}
