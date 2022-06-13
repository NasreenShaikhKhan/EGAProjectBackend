package bankingsystem.backend.service;

import bankingsystem.backend.config.JwtTokenUtil;
import bankingsystem.backend.dao.AccountRepository;
import bankingsystem.backend.dao.UserRepository;
import bankingsystem.backend.dto.Constants;
import bankingsystem.backend.dto.UserDto;
import bankingsystem.backend.entity.Account;
import bankingsystem.backend.entity.User;
import bankingsystem.backend.exception.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.Years;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Transactional
@Slf4j
public class UserService {


    private AccountRepository accountRepository;

    private UserRepository userRepository;


    private AccountService accountService;


    private JwtTokenUtil jwtTokenUtil;



    public UserService(AccountRepository accountRepository,UserRepository userRepository,AccountService accountService,JwtTokenUtil jwtTokenUtil) {
        this.accountRepository = accountRepository;
        this.accountService = accountService;
        this.jwtTokenUtil=jwtTokenUtil;
        this.userRepository=userRepository;
    }



    public String createUser(UserDto userDto) {
        if (accountRepository.findByAccountNo(userDto.getAccountNo()) != null) {
            log.info("user already exist with account number : {}", userDto.getAccountNo());
            throw new BadRequestException(Constants.USER_EXIST);
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        User user=new User();
        user.setPin(encoder.encode(userDto.getPin()));
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setAccount(accountService.createAccount(user,userDto.getAccountNo()));
        userRepository.save(user);
        return Constants.ACCOUNT_CREATED;

    }



    public User getUserFromToken(String token) {
        String accountNo = jwtTokenUtil.getUsernameFromToken(token);
        Account account=accountRepository.findByAccountNo(accountNo);
        return account.getUser();
    }


}
