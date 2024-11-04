package com.devs.dll.ModuloClientePersona.service;

import com.devs.dll.ModuloClientePersona.entity.Cliente;

import java.util.List;

public interface ClienteService {

    Cliente guardarCliente(Cliente cliente);
    List<Cliente> obtenerClientes();
    Cliente actualizarCliente(Long id, Cliente cliente);
    void eliminarCliente(Long id);
    Cliente getClienteById(Long id);
}
