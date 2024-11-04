package com.devs.dll.ModuloClientePersona.controller;

import com.devs.dll.ModuloClientePersona.dto.ClienteDTO;
import com.devs.dll.ModuloClientePersona.entity.Cliente;
import com.devs.dll.ModuloClientePersona.entity.Persona;
import com.devs.dll.ModuloClientePersona.service.ClienteService;
import com.devs.dll.ModuloClientePersona.service.PersonaService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController {
    @Autowired
    private ClienteService clienteService;

    @Autowired
    private PersonaService personaService;

    // Método para convertir ClienteDTO a Cliente
    private Cliente convertToEntity(ClienteDTO clienteDTO) {
        Persona persona = new Persona(
                clienteDTO.getPersona().getNombre(),
                clienteDTO.getPersona().getGenero(),
                clienteDTO.getPersona().getEdad(),
                clienteDTO.getPersona().getIdentificacion(),
                clienteDTO.getPersona().getDireccion(),
                clienteDTO.getPersona().getTelefono()
        );
        return new Cliente(clienteDTO.getPassword(), clienteDTO.isEstado(), persona);
    }

    // Crear un nuevo cliente
    @PostMapping
    @Transactional
    public ResponseEntity<Cliente> createCliente(@RequestBody ClienteDTO clienteDTO) {
        Cliente cliente = convertToEntity(clienteDTO);
        Persona savedPersona = personaService.guardarPersona(cliente.getPersona());
        cliente.setPersona(savedPersona);
        Cliente createdCliente = clienteService.guardarCliente(cliente);
        return ResponseEntity.ok(createdCliente);
    }

    // Obtener todos los clientes
    @GetMapping
    public ResponseEntity<List<Cliente>> getAllClientes() {
        List<Cliente> clientes = clienteService.obtenerClientes();
        return ResponseEntity.ok(clientes);
    }

    // Obtener un cliente por ID
    @GetMapping("/{id}")
    public ResponseEntity<Cliente> getClienteById(@PathVariable Long id) {
        Cliente cliente = clienteService.getClienteById(id);
        return ResponseEntity.ok(cliente);
    }

    // Actualizar un cliente
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<Cliente> updateCliente(@PathVariable Long id, @RequestBody ClienteDTO clienteDTO) {
        Cliente existingCliente = clienteService.getClienteById(id);
        existingCliente.setPassword(clienteDTO.getPassword());
        existingCliente.setEstado(clienteDTO.isEstado());

        Persona personaActual = existingCliente.getPersona();
        personaActual.setNombre(clienteDTO.getPersona().getNombre());
        personaActual.setGenero(clienteDTO.getPersona().getGenero());
        personaActual.setEdad(clienteDTO.getPersona().getEdad());
        personaActual.setDireccion(clienteDTO.getPersona().getDireccion());
        personaActual.setTelefono(clienteDTO.getPersona().getTelefono());

        Cliente updatedCliente = clienteService.guardarCliente(existingCliente);
        return ResponseEntity.ok(updatedCliente);
    }

    // Eliminar un cliente
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> deleteCliente(@PathVariable Long id) {
        clienteService.eliminarCliente(id);
        return ResponseEntity.noContent().build();
    }
}
