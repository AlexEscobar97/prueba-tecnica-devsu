package com.devs.dll.ModuloClientePersona;

import com.devs.dll.ModuloClientePersona.controller.ClienteController;
import com.devs.dll.ModuloClientePersona.dto.ClienteDTO;
import com.devs.dll.ModuloClientePersona.entity.Cliente;
import com.devs.dll.ModuloClientePersona.entity.Persona;
import com.devs.dll.ModuloClientePersona.service.ClienteService;
import com.devs.dll.ModuloClientePersona.service.PersonaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ClienteControllerTest {

    @InjectMocks
    private ClienteController clienteController;

    @Mock
    private ClienteService clienteService;

    @Mock
    private PersonaService personaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createCliente_debeCrearClienteYDevolverResponseEntity() {
        ClienteDTO clienteDTO = new ClienteDTO();
        clienteDTO.setPassword("password");
        clienteDTO.setEstado(true);

        ClienteDTO.PersonaDTO personaDTO = new ClienteDTO.PersonaDTO();
        personaDTO.setNombre("Juan");
        personaDTO.setGenero("Masculino");
        personaDTO.setEdad(30);
        personaDTO.setIdentificacion("12345678");
        personaDTO.setDireccion("Calle 1");
        personaDTO.setTelefono("123456789");

        clienteDTO.setPersona(personaDTO); // Establecer el PersonaDTO en ClienteDTO

        Persona persona = new Persona("Juan", "Masculino", 30, "12345678", "Calle 1", "123456789");
        Cliente cliente = new Cliente("password", true, persona);

        when(personaService.guardarPersona(any(Persona.class))).thenReturn(persona);
        when(clienteService.guardarCliente(any(Cliente.class))).thenReturn(cliente);

        ResponseEntity<Cliente> response = clienteController.createCliente(clienteDTO);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(cliente, response.getBody());
        verify(personaService, times(1)).guardarPersona(any(Persona.class));
        verify(clienteService, times(1)).guardarCliente(any(Cliente.class));
    }


    @Test
    void getAllClientes_debeDevolverListaDeClientes() {
        // Arrange
        List<Cliente> clientes = new ArrayList<>();
        clientes.add(new Cliente());
        clientes.add(new Cliente());

        when(clienteService.obtenerClientes()).thenReturn(clientes);

        // Act
        ResponseEntity<List<Cliente>> response = clienteController.getAllClientes();

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(clientes, response.getBody());
        verify(clienteService, times(1)).obtenerClientes();
    }

    @Test
    void getClienteById_debeDevolverClientePorId() {
        Long clienteId = 1L;
        Cliente cliente = new Cliente();
        when(clienteService.getClienteById(clienteId)).thenReturn(cliente);

        ResponseEntity<Cliente> response = clienteController.getClienteById(clienteId);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(cliente, response.getBody());
        verify(clienteService, times(1)).getClienteById(clienteId);
    }

    @Test
    void updateCliente_debeActualizarClienteYDevolverResponseEntity() {
        // Arrange
        Long clienteId = 1L;
        ClienteDTO clienteDTO = new ClienteDTO();
        clienteDTO.setPassword("newPassword");
        clienteDTO.setEstado(false);

        // Asegúrate de inicializar el campo PersonaDTO en ClienteDTO
        ClienteDTO.PersonaDTO personaDTO = new ClienteDTO.PersonaDTO();
        personaDTO.setNombre("Nuevo Nombre");
        personaDTO.setGenero("Masculino");
        personaDTO.setEdad(25);
        personaDTO.setDireccion("Nueva Direccion");
        personaDTO.setTelefono("987654321");

        clienteDTO.setPersona(personaDTO);

        Cliente existingCliente = new Cliente();
        existingCliente.setPassword("oldPassword");
        existingCliente.setEstado(true);
        existingCliente.setPersona(new Persona());

        when(clienteService.getClienteById(clienteId)).thenReturn(existingCliente);
        when(clienteService.guardarCliente(any(Cliente.class))).thenReturn(existingCliente);

        // Act
        ResponseEntity<Cliente> response = clienteController.updateCliente(clienteId, clienteDTO);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(existingCliente, response.getBody());
        verify(clienteService, times(1)).guardarCliente(existingCliente);
    }


    @Test
    void deleteCliente_debeEliminarCliente() {
        Long clienteId = 1L;

        ResponseEntity<Void> response = clienteController.deleteCliente(clienteId);
        assertNotNull(response);
        assertEquals(204, response.getStatusCodeValue());
        verify(clienteService, times(1)).eliminarCliente(clienteId);
    }
}

