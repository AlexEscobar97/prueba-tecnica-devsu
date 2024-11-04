package com.devs.dll.ModuloCuentaMovimiento.service.impl;

import com.devs.dll.ModuloCuentaMovimiento.entity.Cuenta;
import com.devs.dll.ModuloCuentaMovimiento.repository.CuentaRepository;
import com.devs.dll.ModuloCuentaMovimiento.service.CuentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CuentaServiceImpl implements CuentaService {
    @Autowired
    private CuentaRepository cuentaRepository;

    @Override
    public Cuenta guardarCuenta(Cuenta cuenta) {
        return cuentaRepository.save(cuenta);
    }

    @Override
    public List<Cuenta> obtenerCuentas() {
        return cuentaRepository.findAll();
    }

    @Override
    public Optional<Cuenta> obtenerCuentaPorId(String numeroCuenta) {
        return cuentaRepository.findById(numeroCuenta);
    }

    @Override
    public Cuenta actualizarCuenta(String numeroCuenta, Cuenta cuenta) {
        cuenta.setNumeroCuenta(numeroCuenta);
        return cuentaRepository.save(cuenta);
    }

    @Override
    public void eliminarCuenta(String numeroCuenta) {
        cuentaRepository.deleteById(numeroCuenta);
    }
}
