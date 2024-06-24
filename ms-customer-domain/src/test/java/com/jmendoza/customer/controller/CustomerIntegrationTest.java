package com.jmendoza.customer.controller;

import com.jmendoza.customer.repository.CustomerRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * <h1>Pruebas de integración de Clientes</h1>
 * <ul>
 * <li>Se prueba la comunicación entre los componentes de la solución.</li>
 * </ul>
 * <b>Es necesario que todas las dependencias estén disponibles para validar la integración.</b>
 */
@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CustomerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CustomerRepository customerRepository;

    @BeforeAll
    public void setUp() {
        customerRepository.deleteById("0956257488");
    }

    @Test
    @Order(1)
    public void testFindCustomersOk() throws Exception {
        mockMvc.perform(get("/clientes"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.httpStatus").value(200))
                .andExpect(jsonPath("$.code").value("0"))
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data.customers").isArray());
    }

    @Test
    @Order(2)
    public void createCustomerOk() throws Exception {
        // Arrange
        String jsonContent = "{\n" +
                "    \"password\": \"1245678\",\n" +
                "    \"gender\": \"M\",\n" +
                "    \"age\": 25,\n" +
                "    \"name\": \"Jeanpier Mendoza\",\n" +
                "    \"identification\": \"0956257488\",\n" +
                "    \"phone\": \"0980101842\",\n" +
                "    \"address\": \"Toscana\"\n" +
                "}";

        // Act
        mockMvc.perform(post("/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.httpStatus").value(200))
                .andExpect(jsonPath("$.code").value("0"))
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data.identification").value("0956257488"))
                .andExpect(jsonPath("$.data.name").value("Jeanpier Mendoza"));
    }

    @Test
    @Order(3)
    public void getCustomerByIdentificationOk() throws Exception {
        mockMvc.perform(get("/clientes/{identification}", "0956257488"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.httpStatus").value(200))
                .andExpect(jsonPath("$.code").value("0"))
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data.identification").value("0956257488"))
                .andExpect(jsonPath("$.data.name").value("Jeanpier Mendoza"));
    }

    @Test
    @Order(4)
    public void updateCustomerOk() throws Exception {
        // Arrange
        String jsonContent = "{\"name\": \"Jeanpier Mendoza Navarro\"}";

        // Act
        mockMvc.perform(patch("/clientes/{identification}", "0956257488")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.httpStatus").value(200))
                .andExpect(jsonPath("$.code").value("0"))
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data.name").value("Jeanpier Mendoza Navarro"));
    }

    @Test
    @Order(5)
    public void deleteCustomerTestOk() throws Exception {
        mockMvc.perform(delete("/clientes/{identification}", "0956257488"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.httpStatus").value(200))
                .andExpect(jsonPath("$.code").value("0"));
    }
}
