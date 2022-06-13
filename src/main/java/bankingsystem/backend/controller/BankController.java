package bankingsystem.backend.controller;

import bankingsystem.backend.dto.*;
import bankingsystem.backend.entity.Account;
import bankingsystem.backend.entity.Transaction;
import bankingsystem.backend.exception.BadRequestException;
import bankingsystem.backend.service.AccountService;
import bankingsystem.backend.service.TransactionService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/secure/account")
@CrossOrigin
public class BankController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private TransactionService transactionService;

    private final Logger logger = LogManager.getLogger(getClass());

    @GetMapping("/getDetails")
    public ResponseEntity<AccountDto> getAccountDetails(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("Entered");
        try {
            return ResponseEntity.ok(accountService.getAccountFromToken(request.getHeader("access_token")));
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/deposit")
    public ResponseEntity<Response> depositMoney(@RequestBody TransferMoneyDto transferMoneyDto, HttpServletRequest request) {
        try {
            return ResponseEntity.ok(new Response(Constants.SUCCESS, transactionService.depositMoney(transferMoneyDto, request.getHeader("access_token"))));
        } catch (BadRequestException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response(Constants.ERROR, e.getMessage()));
        } catch (Exception e) {
            logger.error("error produced during transfer money : {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response(Constants.ERROR, e.getMessage()));
        }
    }
    @PostMapping("/withdraw")
    public ResponseEntity<Response> withdrawMoney(@RequestBody TransferMoneyDto transferMoneyDto, HttpServletRequest request) {
        try {
            return ResponseEntity.ok(new Response(Constants.SUCCESS, transactionService.withdrawMoney(transferMoneyDto, request.getHeader("access_token"))));
        } catch (BadRequestException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response(Constants.ERROR, e.getMessage()));
        } catch (Exception e) {
            logger.error("error produced during transfer money : {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response(Constants.ERROR, e.getMessage()));
        }
    }
    @GetMapping("/transaction")
    public ResponseEntity<PageDto> getTransactionHistory(HttpServletRequest request, @RequestParam("pageNo") int pageNo) {
        try {
            return ResponseEntity.ok(transactionService.getTransactionHistory(request.getHeader("access_token"),pageNo));
        } catch (Exception e) {
            logger.error("error produced getting transaction history : {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new PageDto());
        }
    }

}
