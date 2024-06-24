package com.jmendoza.customer.service;

import com.jmendoza.customer.core.enums.Gender;
import com.jmendoza.customer.core.enums.ResponseDictionary;
import com.jmendoza.customer.core.exception.ConflictException;
import com.jmendoza.customer.core.exception.NotFoundException;
import com.jmendoza.customer.domain.Customer;
import com.jmendoza.customer.dto.CreateCustomerDto;
import com.jmendoza.customer.dto.CustomerDto;
import com.jmendoza.customer.dto.UpdateCustomerDto;
import com.jmendoza.customer.dto.UpdatePasswordDto;
import com.jmendoza.customer.repository.CustomerRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.yaml.snakeyaml.util.EnumUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Encontrar todos los clientes
     *
     * @return Una lista de clientes
     */
    @Override
    public List<CustomerDto> find() {
        var customers = customerRepository.findAll()
                .stream()
                .map(CustomerDto::new)
                .toList();
        log.info("Clientes obtenidos");
        return customers;
    }

    /**
     * Encontrar un cliente por ID
     *
     * @param id El ID del cliente
     * @return El cliente encontrado
     */
    @Override
    public CustomerDto find(String id) {
        var customer = findByIdAndValidateExists(id);
        log.info("Cliente obtenido con ID {}", customer.getPersonId());
        return new CustomerDto(customer);
    }

    /**
     * Encontrar un cliente por ID y validar que exista
     *
     * @param id El ID del cliente
     * @return El cliente encontrado
     */
    private Customer findByIdAndValidateExists(String id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ResponseDictionary.CUSTOMER_NOT_FOUND, id));
    }

    /**
     * Crear un cliente
     *
     * @param dto Los datos del cliente
     * @return El cliente creado
     */
    @Override
    @Transactional
    public CustomerDto create(CreateCustomerDto dto) {
        var customerAlreadyExists = this.customerRepository.findById(dto.getIdentification()).isPresent();
        if (customerAlreadyExists) {
            throw new ConflictException(ResponseDictionary.CUSTOMER_ALREADY_EXISTS, dto.getIdentification());
        }
        Customer customer = new Customer(dto, passwordEncoder);
        customer = this.customerRepository.save(customer);
        log.info("Cliente creado con ID {}", customer.getPersonId());
        return new CustomerDto(customer);
    }

    /**
     * Actualizar un cliente por ID
     *
     * @param id  El ID del cliente
     * @param dto Los datos del cliente
     * @return El cliente actualizado
     */
    @Override
    @Transactional
    public CustomerDto update(String id, UpdateCustomerDto dto) {
        var customer = findByIdAndValidateExists(id);

        customer.setName(dto.getName());
        customer.setGender(EnumUtils.findEnumInsensitiveCase(Gender.class, dto.getGender()));
        customer.setAge(dto.getAge());
        customer.setAddress(dto.getAddress());
        customer.setPhone(dto.getPhone());
        log.info("Cliente actualizado con ID {}", customer.getPersonId());
        return new CustomerDto(customer);
    }

    /**
     * Actualizar un cliente por ID
     *
     * @param id  El ID del cliente
     * @param dto Los datos del cliente
     * @return El cliente actualizado
     */
    @Transactional
    public CustomerDto partialUpdate(String id, UpdateCustomerDto dto) {
        var customer = findByIdAndValidateExists(id);

        if (StringUtils.hasText(dto.getName())) customer.setName(dto.getName());
        if (dto.getGender() != null)
            customer.setGender(EnumUtils.findEnumInsensitiveCase(Gender.class, dto.getGender()));
        if (dto.getAge() != null) customer.setAge(dto.getAge());
        if (StringUtils.hasText(dto.getAddress())) customer.setAddress(dto.getAddress());
        if (StringUtils.hasText(dto.getPhone())) customer.setPhone(dto.getPhone());

        log.info("Cliente actualizado parcialmente con ID {}", customer.getPersonId());
        return new CustomerDto(customer);
    }

    /**
     * Actualizar la contraseña de un cliente por ID
     *
     * @param id  El ID del cliente
     * @param dto Los datos de la nueva contraseña
     */
    @Transactional
    public void updatePassword(String id, UpdatePasswordDto dto) {
        var customer = findByIdAndValidateExists(id);
        customer.setPassword(passwordEncoder.encode(dto.getPassword()));
        log.info("Password actualizado de cliente con ID {}", customer.getPersonId());
    }

    /**
     * Eliminar un cliente por ID
     *
     * @param id El ID del cliente
     */
    @Override
    @Transactional
    public void delete(String id) {
        var customer = findByIdAndValidateExists(id);
//        customer.setState(Boolean.FALSE);
        customerRepository.delete(customer);
        log.info("Cliente eliminado con ID {}", customer.getPersonId());
    }

    /**
     * Desactivar un cliente por ID
     *
     * @param id El ID del cliente
     */
    @Override
    @Transactional
    public void deactivate(String id) {
        var customer = findByIdAndValidateExists(id);
        customer.deactivate();
        log.info("Cliente desactivado con ID {}", customer.getPersonId());
    }

    /**
     * Activar un cliente por ID
     *
     * @param id El ID del cliente
     */
    @Override
    @Transactional
    public void activate(String id) {
        var customer = findByIdAndValidateExists(id);
        customer.activate();
        log.info("Cliente activado con ID {}", customer.getPersonId());
    }
}
