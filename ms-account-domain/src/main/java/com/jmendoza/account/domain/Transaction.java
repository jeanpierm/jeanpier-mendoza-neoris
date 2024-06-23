package com.jmendoza.account.domain;

import com.jmendoza.account.core.enums.ResponseDictionary;
import com.jmendoza.account.core.enums.TransactionType;
import com.jmendoza.account.core.exception.BadRequestException;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "transaction")
@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true)
public class Transaction extends BaseEntity {

    @Id
    @Setter(AccessLevel.NONE)
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "transaction_id", nullable = false, updatable = false)
    private String transactionId;

    @Column(name = "transaction_type", nullable = false)
    private TransactionType type;

    @Column(name = "value", nullable = false)
    private BigDecimal value;

    /**
     * Saldo previo al movimiento
     */
    @Column(name = "previous_balance", nullable = false)
    private BigDecimal previousBalance;

    /**
     * Saldo luego del movimiento
     */
    @Column(name = "balance", nullable = false)
    private BigDecimal balance;

    /**
     * Cuenta que realizó el movimiento
     */
    @ManyToOne
    @JoinColumn(name = "account_number", nullable = false)
    private Account account;

    /**
     * Constructor de la clase
     * Verifica que el valor del movimiento no sea $0
     * Verifica que la cuenta tenga saldo suficiente para el movimiento
     * Asigna el tipo de movimiento (CRÉDITO o DÉBITO)
     * Asigna el saldo de la cuenta luego del movimiento
     *
     * @param value   Valor del movimiento
     * @param account Cuenta a la que se le asigna el movimiento
     */
    public Transaction(BigDecimal value, Account account) {
        setValue(value);
        this.account = account;
        this.previousBalance = account.getBalance();
        this.balance = this.account.getBalance().add(value);
    }

    /**
     * Asigna el valor del movimiento
     * Verifica que el valor no sea $0
     *
     * @param value Valor del movimiento
     */
    public void setValue(BigDecimal value) {
        // no puede haber un movimiento de $0
        if (value.compareTo(BigDecimal.ZERO) == 0) throw new BadRequestException(ResponseDictionary.INSUFFICIENT_BALANCE);
        this.value = value;
        this.type = this.isCredit() ? TransactionType.CREDIT : TransactionType.DEBIT;
    }

    public boolean isCredit() {
        return value.compareTo(BigDecimal.ZERO) > 0;
    }
}
