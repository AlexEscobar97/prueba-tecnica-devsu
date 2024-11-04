package com.devs.dll.ModuloCuentaMovimiento.repository;

import com.devs.dll.ModuloCuentaMovimiento.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    @Query(value = "SELECT clienteid, password, estado, id_persona FROM cliente WHERE clienteid = :clienteid", nativeQuery = true)
    Optional<Cliente> findByClienteId(@Param("clienteid") Long clienteId);
}
