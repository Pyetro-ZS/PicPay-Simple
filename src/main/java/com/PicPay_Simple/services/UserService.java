package com.PicPay_Simple.services;

import com.PicPay_Simple.domain.user.User;
import com.PicPay_Simple.domain.user.UserType;
import com.PicPay_Simple.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public void validateTransaction(User sender, BigDecimal amount) throws Exception {
        if (sender.getUserType() == UserType.MERCHANT){
            throw new Exception("Usuários do tipo Lojista não estão autorizados a realizar transações.");
        }

        if (sender.getBalance().compareTo(amount) < 0) {
            throw new Exception("Saldo insuficiente para realizar a transação.");
        }
    }

    public User findUserById(Long id) throws Exception {
        return repository.findUserById(id).orElseThrow(() -> new RuntimeException("Usuário não encontrado."));
    }

    public void saveUser(User user) {
        repository.save(user);
    }
}
