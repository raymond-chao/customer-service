package com.example.customer_service;


import com.example.customer_service.client.BookingClient;
import com.example.customer_service.service.CustomerService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@Transactional
class CustomerServiceApplicationTests {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BookingClient bookingClient;



    @Test
    void skapaKundGer201() throws Exception {
        mockMvc.perform(post("/api/customers").contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content("{\"name\":\"Ray\",\"email\":\"test@test.com\",\"phoneNumber\":\"0712312312\",\"password\":\"test\"}"))
                .andExpect(status().isCreated());
    }


}