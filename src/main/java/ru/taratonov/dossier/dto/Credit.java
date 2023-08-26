package ru.taratonov.dossier.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.List;


@Data
@Accessors(chain = true)

public class Credit {

    private BigDecimal amount;

    private Integer term;

    private BigDecimal monthlyPayment;

    private BigDecimal rate;

    private BigDecimal psk;

    private List<PaymentScheduleElement> paymentSchedule;

    private Boolean insuranceEnable;

    private Boolean salaryClient;
}
