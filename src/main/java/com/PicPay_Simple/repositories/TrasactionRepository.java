package com.PicPay_Simple.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.PicPay_Simple.domain.transaction.Transaction;

public interface TrasactionRepository extends JpaRepository<Transaction, Long> {
}
