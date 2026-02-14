package com.PicPay_Simple.repositories;

import com.PicPay_Simple.domain.transaction.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long>
{
}
