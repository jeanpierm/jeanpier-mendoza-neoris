package com.jmendoza.account.core.configuration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class DatabaseInitializer implements CommandLineRunner {

    private static final String CREATE_FK_ACCOUNT_CUSTOMER = "ALTER TABLE account ADD CONSTRAINT fk_customer FOREIGN KEY (customer_id) REFERENCES customer(customer_id);";

    private final JdbcTemplate jdbcTemplate;

    @Override
    public void run(String... args) {
        createFkAccountCustomer();
    }

    private void createFkAccountCustomer() {
        try {
            jdbcTemplate.execute(CREATE_FK_ACCOUNT_CUSTOMER);
            log.info("Relacion FK entre cuenta y cliente creada con exito.");
        } catch (Exception e) {
            log.error("Ocurrio un crear FK entre cuenta y cliente: {}", e.getMessage());
        }
    }
}
