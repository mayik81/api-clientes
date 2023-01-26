package com.mayik.springboot.backend.apirest.services;

import com.mayik.springboot.backend.apirest.models.dao.IClienteDao;
import com.mayik.springboot.backend.apirest.models.entity.Cliente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class IClienteServiceTest {

    @Mock
    private IClienteDao clienteDao;

    @InjectMocks
    private IClienteServiceImpl clienteService;

    private Cliente cliente;

    @BeforeEach
    void setup(){
        cliente = Cliente.builder().nombre("Roberto").apellido("López").email("mayik@mail.com").build();
    }

    @Test
    void testGuardarCliente() throws Exception{
        given(clienteDao.save(any(Cliente.class))).willReturn(cliente);
        Cliente clienteGuardado = clienteService.save(cliente);
        assertEquals(clienteGuardado.getEmail(),cliente.getEmail());

    }

    @Test
    void testObtenerClientes() throws Exception{
        Cliente cliente1 = Cliente.builder().nombre("Roberto").apellido("López").email("mayik@mail.com").build();
        Cliente cliente2 = Cliente.builder().nombre("María").apellido("García").email("maria@mail.com").build();
        List clientes2 = List.of(cliente1, cliente2);

        given(clienteDao.findAll()).willReturn(clientes2);
        List<Cliente> clientesObtenidos = clienteService.findAll();
        assertEquals(clientesObtenidos.size(),clientes2.size());
    }


}