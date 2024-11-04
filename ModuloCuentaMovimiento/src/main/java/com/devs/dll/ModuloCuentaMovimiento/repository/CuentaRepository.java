package com.devs.dll.ModuloCuentaMovimiento.repository;

import com.devs.dll.ModuloCuentaMovimiento.entity.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CuentaRepository extends JpaRepository<Cuenta, String> {

    @Query(value = "SELECT numero_cuenta, tipo_cuenta, saldo_inicial, estado, clienteid FROM cuenta WHERE numero_cuenta = :numeroCuenta", nativeQuery = true)
    Optional<Cuenta> findCuentaByNumeroCuenta(@Param("numeroCuenta") String numeroCuenta);
}
