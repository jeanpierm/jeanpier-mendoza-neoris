package com.jmendoza.customer.service;

import com.jmendoza.customer.core.enums.Gender;
import com.jmendoza.customer.core.exception.ConflictException;
import com.jmendoza.customer.core.exception.NotFoundException;
import com.jmendoza.customer.domain.Customer;
import com.jmendoza.customer.dto.CreateCustomerDto;
import com.jmendoza.customer.dto.CustomerDto;
import com.jmendoza.customer.dto.UpdateCustomerDto;
import com.jmendoza.customer.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * <h1>Pruebas unitarias del dominio Cliente</h1>
 * <ul>
 * <li>Se prueba la capa de servicio debido a que es la que contiene la mayor parte de la lógica de negocio.</li>
 * <li>Se utiliza Mockito para simular el comportamiento de la capa de persistencia.</li>
 * <li>Se utiliza JUnit 5 para las pruebas unitarias.</li>
 * </ul>
 */
@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private CustomerServiceImpl customerService;

    @Test
    public void testFindAllCustomersOk() {
        // Arrange
        List<Customer> customers = customerStub();
        when(customerRepository.findAll()).thenReturn(customers);

        // Act
        List<CustomerDto> result = customerService.find();

        // Assert
        assertNotNull(result);
        assertFalse(result.isEmpty());
    }

    @Test
    public void testFindCustomerOk() {
        // Arrange
        Customer customer = customerStub().getFirst();
        when(customerRepository.findById("0956257497")).thenReturn(Optional.of(customer));

        // Act
        CustomerDto result = customerService.find("0956257497");

        // Assert
        assertNotNull(result);
        assertEquals("0956257497", result.getIdentification());
        assertEquals("José Lema", result.getName());
        assertEquals("M", result.getGender());
        assertEquals("Otavalo sn y principal", result.getAddress());
        assertEquals((short) 30, result.getAge());
        assertEquals("098254785", result.getPhone());
        assertEquals("Activo", result.getState());
    }

    @Test
    public void testCreateCustomerOk() {
        // Arrange
        Customer customer = customerStub().getFirst();
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);
        when(passwordEncoder.encode("12345678")).thenReturn("mockEncryptedPassword");

        // Act
        when(customerRepository.findById("0956257497")).thenReturn(Optional.empty());
        CustomerDto result = customerService.create(createCustomerStub());

        // Assert
        assertNotNull(result);
        assertEquals("0956257497", result.getIdentification());
        assertEquals("José Lema", result.getName());
        assertEquals("M", result.getGender());
        assertEquals("Otavalo sn y principal", result.getAddress());
        assertEquals((short) 30, result.getAge());
        assertEquals("098254785", result.getPhone());
        assertEquals("Activo", result.getState());
    }

    @Test
    public void testCreateCustomerWithExistingIdentificationError() {
        // Arrange
        when(customerRepository.findById("0956257497")).thenReturn(Optional.of(customerStub().getFirst()));

        // Act and Assert
        assertThrows(ConflictException.class, () -> customerService.create(createCustomerStub()));
    }

    @Test
    public void testUpdateCustomerOk() {
        // Arrange
        Customer customer = customerStub().getFirst();
        when(customerRepository.findById("0956257497")).thenReturn(Optional.of(customer));

        // Act
        CustomerDto result = customerService.update("0956257497", updateCustomerStub());

        // Assert
        assertNotNull(result);
        assertEquals("0956257497", result.getIdentification());
        assertEquals("Marianela Montalvo", result.getName());
        assertEquals("F", result.getGender());
        assertEquals("Amazonas y NNUU", result.getAddress());
        assertEquals((short) 28, result.getAge());
        assertEquals("097548965", result.getPhone());
        assertEquals("Activo", result.getState());
    }

    @Test
    public void testUpdateCustomerWithNonExistingIdentificationError() {
        // Arrange
        when(customerRepository.findById("0956257497")).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(NotFoundException.class, () -> customerService.update("0956257497", updateCustomerStub()));
    }

    @Test
    public void testDeleteCustomerOk() {
        // Arrange
        Customer customer = customerStub().getFirst();
        when(customerRepository.findById("0956257497")).thenReturn(Optional.of(customer));

        // Act
        customerService.delete("0956257497");

        // Assert
        verify(customerRepository).delete(customer);
    }

    @Test
    public void testDeleteCustomerWithNonExistingIdentificationError() {
        // Arrange
        when(customerRepository.findById("0956257497")).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(NotFoundException.class, () -> customerService.delete("0956257497"));
    }

    private static List<Customer> customerStub() {
        List<Customer> customers = new ArrayList<>();
        Customer customer = new Customer();
        customer.setPersonId("0956257497");
        customer.setName("José Lema");
        customer.setGender(Gender.M);
        customer.setAddress("Otavalo sn y principal");
        customer.setAge((short) 30);
        customer.setPhone("098254785");
        customer.setPassword("mockEncryptedPassword");
        customer.setState(Boolean.TRUE);
        customers.add(customer);
        return customers;
    }

    private static CreateCustomerDto createCustomerStub() {
        CreateCustomerDto createCustomerDto = new CreateCustomerDto();
        createCustomerDto.setIdentification("0956257497");
        createCustomerDto.setName("José Lema");
        createCustomerDto.setGender("M");
        createCustomerDto.setAddress("Otavalo sn y principal");
        createCustomerDto.setAge((short) 30);
        createCustomerDto.setPhone("098254785");
        createCustomerDto.setPassword("12345678");
        return createCustomerDto;
    }

    private static UpdateCustomerDto updateCustomerStub() {
        UpdateCustomerDto updateCustomerDto = new UpdateCustomerDto();
        updateCustomerDto.setName("Marianela Montalvo");
        updateCustomerDto.setGender("F");
        updateCustomerDto.setAddress("Amazonas y NNUU");
        updateCustomerDto.setAge((short) 28);
        updateCustomerDto.setPhone("097548965");
        return updateCustomerDto;
    }
}
