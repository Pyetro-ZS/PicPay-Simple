package com.PicPay_Simple.services;

import com.PicPay_Simple.domain.transaction.Transaction;
import com.PicPay_Simple.domain.user.User;
import com.PicPay_Simple.dtos.TransactionDTO;
import com.PicPay_Simple.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

@Service
public class TransactionService {

    @Autowired
    private UserService userService;

    @Autowired
    private TransactionRepository repository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private NotificationService notificationService;

    public Transaction createTransaction(TransactionDTO transaction) throws Exception {
        User sender = this.userService.findUserById(transaction.sanderId());
        User receiver = this.userService.findUserById(transaction.receiverId());

        userService.validateTransaction(sender, transaction.value());

        boolean isAuthorized = this.authorizeTransaction(sender, transaction.value());
        if (this.authorizeTransaction(sender, transaction.value())) {
            throw new Exception("Transação não autorizada.");
        }

        Transaction newTransaction = new Transaction();
        newTransaction.setAmount(transaction.value());
        newTransaction.setSender(sender);
        newTransaction.setReceiver(receiver);
        newTransaction.setTimestamp(LocalDateTime.now());

        sender.setBalance(sender.getBalance().subtract(transaction.value()));
        receiver.setBalance(receiver.getBalance().add(transaction.value()));

        this.repository.save(newTransaction);
        this.userService.saveUser(sender);
        this.userService.saveUser(receiver);

        this.notificationService.sendNotification(sender, "Transação realizada com sucesso. Valor: " + transaction.value());
        this.notificationService.sendNotification(receiver, "Transação Recebida com sucesso. Valor: " + transaction.value());


        return newTransaction;
    }


    public boolean authorizeTransaction(User sender, BigDecimal value) {
        ResponseEntity<Map> authorizationResponse = restTemplate.getForEntity("https://run.mocky.io/v3/8fafdd68-a098-496f-8c9a-344cf30dae6", Map.class);

        if (authorizationResponse.getStatusCode() == HttpStatus.OK) {
            String massage = (String) authorizationResponse.getBody().get("message");
            return "Autorizado".equalsIgnoreCase(massage);
        } else return false;
        }
}

