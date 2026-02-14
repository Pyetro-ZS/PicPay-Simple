package com.PicPay_Simple.dtos;

import java.math.BigDecimal;

public record TransactionDTO(BigDecimal value, long sanderId, long receiverId) {
}
