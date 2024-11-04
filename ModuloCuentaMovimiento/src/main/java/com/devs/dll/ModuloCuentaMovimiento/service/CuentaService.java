package com.devs.dll.ModuloCuentaMovimiento.service;

import com.devs.dll.ModuloCuentaMovimiento.entity.Cuenta;

import java.util.List;
import java.util.Optional;

public interface CuentaService {

    Cuenta guardarCuenta(Cuenta cuenta);

    List<Cuenta> obtenerCuentas();

    Optional<Cuenta> obtenerCuentaPorId(String numeroCuenta);

    Cuenta actualizarCuenta(String numeroCuenta, Cuenta cuenta);

    void eliminarCuenta(String numeroCuenta);
}
