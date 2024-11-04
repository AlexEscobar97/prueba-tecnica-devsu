package com.devs.dll.ModuloClientePersona.repository;

import com.devs.dll.ModuloClientePersona.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
