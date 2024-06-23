package com.jmendoza.account.domain;

import com.jmendoza.account.core.enums.AccountType;
import com.jmendoza.account.dto.CreateAccountDto;
import jakarta.persistence.*;
import lombok.*;
import org.yaml.snakeyaml.util.EnumUtils;

import java.math.BigDecimal;

@Entity
@Table(name = "account")
@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true)
public class Account extends BaseEntity {

    public static final int NUMBER_LENGTH_AS_STR = 10;
    public static final int CUSTOMER_ID_LENGTH = 13;

    /**
     * Número único de la cuenta
     */
    @Id
    @Setter(AccessLevel.NONE)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "number", nullable = false, updatable = false)
    private Long number;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private AccountType type;

    @Column(name = "customer_id", nullable = false, length = CUSTOMER_ID_LENGTH)
    private String customerId;

    @Column(name = "balance", nullable = false)
    private BigDecimal balance;

    @Column(name = "state", nullable = false)
    private Boolean state;

    public Account(CreateAccountDto createAccountDto) {
        this.type = EnumUtils.findEnumInsensitiveCase(AccountType.class, createAccountDto.getType());
        this.customerId = createAccountDto.getCustomerId();
        this.balance = createAccountDto.getBalance();
        this.state = Boolean.TRUE;
    }

    public void deactivate() {
        this.state = Boolean.FALSE;
    }

    public void activate() {
        this.state = Boolean.TRUE;
    }


    /**
     * Verificar si la cuenta tiene saldo suficiente
     *
     * @param value Valor de la transacción
     * @return Si la cuenta tiene saldo suficiente
     */
    public boolean hasSufficientBalanceForTransaction(BigDecimal value) {
        var isCredit = value.compareTo(BigDecimal.ZERO) > 0;
        if (isCredit) return true; // si es credito no es necesario validar
        return balance.compareTo(value.abs()) >= 0; // balance es mayor o igual al valor absoluto de la transaccion
    }

    public void updateBalance(BigDecimal value) {
        this.balance = this.balance.add(value);
    }

    public void reverseBalance(BigDecimal value) {
        this.balance = this.balance.subtract(value);
    }
}
