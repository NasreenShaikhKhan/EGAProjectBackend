package bankingsystem.backend.service;

import bankingsystem.backend.config.JwtTokenUtil;
import bankingsystem.backend.dao.AccountRepository;
import bankingsystem.backend.dao.TransactionRepository;
import bankingsystem.backend.dto.Constants;
import bankingsystem.backend.dto.PageDto;
import bankingsystem.backend.dto.TransactionDto;
import bankingsystem.backend.dto.TransferMoneyDto;
import bankingsystem.backend.entity.Account;
import bankingsystem.backend.entity.Transaction;
import bankingsystem.backend.exception.BadRequestException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.MathContext;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class TransactionService {


    private TransactionRepository transactionRepository;

    private AccountRepository accountRepository;


    private AccountService accountService;


    private JwtTokenUtil jwtTokenUtil;

    @Value("${page.size}")
    private int pageSize;

    public TransactionService(AccountService accountService, TransactionRepository transactionRepository, AccountRepository accountRepository
    , JwtTokenUtil jwtTokenUtil)
    {
        this.accountService=accountService;
        this.transactionRepository=transactionRepository;
        this.accountRepository=accountRepository;
        this.jwtTokenUtil=jwtTokenUtil;
    }

    private final Logger logger = LogManager.getLogger(getClass());

    public String depositMoney(TransferMoneyDto transferMoneyDto, String token) {
        String transferFrom = jwtTokenUtil.getUsernameFromToken(token);


        Account accountFrom = accountService.getAccountByAccountNo(transferFrom);

        return makeTransaction(accountFrom, transferMoneyDto.getAmount());




    }
    public String withdrawMoney(TransferMoneyDto transferMoneyDto, String token) {
         String transferFrom = jwtTokenUtil.getUsernameFromToken(token);
        Account account = accountService.getAccountByAccountNo(transferFrom);

        if (account == null) {
            logger.info("Account not found with account no : {}", transferFrom);
            throw new BadRequestException("Account not found");
        }
   
        if (account.getBalance().compareTo(transferMoneyDto.getAmount())<0 ) {
            logger.error("Not enough balance in account : {}", transferFrom);
            throw new BadRequestException("Not enough balance");
        }

        return makeWithdrawal(account, transferMoneyDto.getAmount());



    }
    private String makeTransaction(Account accountFrom, BigDecimal amount) {
        Transaction transaction = new Transaction();
        accountFrom.addTransaction(transaction);
        transaction.setType(Constants.CREDIT);
        transaction.setAmount(amount);
        transaction.setDate(new Date());
        accountFrom.setBalance(accountFrom.getBalance().add(amount));
        accountRepository.save(accountFrom);
        return Constants.TRANSACTION_DONE;


    }
    private String makeWithdrawal(Account accountFrom, BigDecimal amount) {
        Transaction transaction = new Transaction();
        transaction.setType(Constants.DEBIT);
        transaction.setDate(new Date());
        transaction.setAmount(amount);
        accountFrom.addTransaction(transaction);
        //MathContext mc = new MathContext(2)
        accountFrom.setBalance(accountFrom.getBalance().subtract(amount));
        accountRepository.save(accountFrom);
       // accountService.updateAccount(accountFrom);
        return Constants.TRANSACTION_DONE;



    }
    public PageDto getTransactionHistory(String token,int pageNo) {
        String accountNo = jwtTokenUtil.getUsernameFromToken(token);
        Page<Transaction> transactions =transactionRepository.findByAccountAccountNo(accountNo, PageRequest.of(pageNo,pageSize));
        List<TransactionDto> transactionList= transactions.getContent().stream().map(x->mapToTransactionDto(x)).collect(Collectors.toList());
        PageDto pageDto=new PageDto();
        pageDto.setTransactionList(transactionList);
        pageDto.setTotalCount(String.valueOf(transactions.getTotalElements()));
        return pageDto;
    }

    private TransactionDto mapToTransactionDto(Transaction transaction)
    {
        String pattern = "MM/dd/yyyy HH:mm:ss";

        DateFormat df = new SimpleDateFormat(pattern);
        return TransactionDto.builder().transactionId(transaction.getId().toString()).amount(transaction.getAmount()).date(df.format(transaction.getDate()))
                .type(transaction.getType()).build();

    }
}
