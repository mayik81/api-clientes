package com.mayik.springboot.backend.apirest.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mayik.springboot.backend.apirest.models.entity.Cliente;
import com.mayik.springboot.backend.apirest.services.IClienteService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.RestController;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;



@WebMvcTest(RestController.class)
class ClienteRestControllerTest {

    @MockBean
    private IClienteService clienteService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    void testGuardarCliente() throws Exception{
        Cliente cliente1 = Cliente.builder().nombre("Roberto").apellido("López").email("mayik@mail.com").build();

        when(clienteService.save(any(Cliente.class))).thenReturn(cliente1);

        mockMvc.perform(post("/api/clientes").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cliente1)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.cliente.nombre").value(cliente1.getNombre()))
                .andDo(print());
    }

    @Test
    void testRetornarListadeClientes() throws Exception{
        Cliente cliente1 = Cliente.builder().nombre("Roberto").apellido("López").email("mayik@mail.com").build();
        Cliente cliente2 = Cliente.builder().nombre("María").apellido("García").email("maria@mail.com").build();
        Cliente cliente3 = Cliente.builder().nombre("Pepe").apellido("Sánchez").email("pepe@mail.com").build();
        List<Cliente> clientes = new ArrayList<>();
        clientes.add(cliente1);
        clientes.add(cliente2);
        clientes.add(cliente3);

        when(clienteService.findAll()).thenReturn(clientes);

        mockMvc.perform(get("/api/clientes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(clientes.size()))
                .andDo(print());
    }
}