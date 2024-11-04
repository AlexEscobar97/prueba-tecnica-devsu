package com.devs.dll.ModuloCuentaMovimiento.controller;

import com.devs.dll.ModuloCuentaMovimiento.dto.CuentaDTO;
import com.devs.dll.ModuloCuentaMovimiento.entity.Cliente;
import com.devs.dll.ModuloCuentaMovimiento.entity.Cuenta;
import com.devs.dll.ModuloCuentaMovimiento.service.CuentaService;
import com.devs.dll.ModuloCuentaMovimiento.repository.ClienteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/cuentas")
public class CuentaController {

    private static final Logger logger = LoggerFactory.getLogger(CuentaController.class);

    @Autowired
    private CuentaService cuentaService;

    @Autowired
    private ClienteRepository clienteRepository;

    @PostMapping
    public Cuenta crearCuenta(@RequestBody CuentaDTO cuentaDTO) {
        logger.info("Creando cuenta: {}", cuentaDTO);
        Cuenta cuenta = new Cuenta();
        cuenta.setNumeroCuenta(cuentaDTO.getNumeroCuenta());
        cuenta.setTipoCuenta(cuentaDTO.getTipoCuenta());
        cuenta.setSaldoInicial(cuentaDTO.getSaldoInicial());
        cuenta.setEstado(cuentaDTO.getEstado());

        // Buscar el cliente por ID
        Optional<Cliente> clienteOpt = clienteRepository.findById(cuentaDTO.getCliente());
        if (clienteOpt.isPresent()) {
            cuenta.setCliente(clienteOpt.get());
        } else {
            logger.error("Cliente no encontrado con ID: {}", cuentaDTO.getCliente());
            throw new RuntimeException("Cliente no encontrado con ID: " + cuentaDTO.getCliente());
        }

        Cuenta savedCuenta = cuentaService.guardarCuenta(cuenta);
        logger.info("Cuenta creada con éxito: {}", savedCuenta);
        return savedCuenta;
    }

    @GetMapping
    public List<CuentaDTO> obtenerCuentas() {
        logger.info("Obteniendo todas las cuentas");
        return cuentaService.obtenerCuentas().stream().map(cuenta -> {
            CuentaDTO cuentaDTO = new CuentaDTO();
            cuentaDTO.setNumeroCuenta(cuenta.getNumeroCuenta());
            cuentaDTO.setTipoCuenta(cuenta.getTipoCuenta());
            cuentaDTO.setSaldoInicial(cuenta.getSaldoInicial());
            cuentaDTO.setEstado(cuenta.getEstado());
            return cuentaDTO;
        }).collect(Collectors.toList());
    }

    @GetMapping("/{numeroCuenta}")
    public CuentaDTO obtenerCuentaPorId(@PathVariable String numeroCuenta) {
        logger.info("Obteniendo cuenta por ID: {}", numeroCuenta);
        return cuentaService.obtenerCuentaPorId(numeroCuenta).map(cuenta -> {
            CuentaDTO cuentaDTO = new CuentaDTO();
            cuentaDTO.setNumeroCuenta(cuenta.getNumeroCuenta());
            cuentaDTO.setTipoCuenta(cuenta.getTipoCuenta());
            cuentaDTO.setSaldoInicial(cuenta.getSaldoInicial());
            cuentaDTO.setEstado(cuenta.getEstado());
            cuentaDTO.setCliente(cuenta.getCliente().getClienteid());
            return cuentaDTO;
        }).orElse(null);
    }

    @PutMapping("/{numeroCuenta}")
    public Cuenta actualizarCuenta(@PathVariable String numeroCuenta, @RequestBody CuentaDTO cuentaDTO) {
        logger.info("Actualizando cuenta con ID: {}", numeroCuenta);
        Cuenta cuenta = new Cuenta();
        cuenta.setNumeroCuenta(numeroCuenta);
        cuenta.setTipoCuenta(cuentaDTO.getTipoCuenta());
        cuenta.setSaldoInicial(cuentaDTO.getSaldoInicial());
        cuenta.setEstado(cuentaDTO.getEstado());

        // Buscar el cliente por ID
        Optional<Cliente> clienteOpt = clienteRepository.findById(cuentaDTO.getCliente());
        if (clienteOpt.isPresent()) {
            cuenta.setCliente(clienteOpt.get());
        } else {
            logger.error("Cliente no encontrado con ID: {}", cuentaDTO.getCliente());
            throw new RuntimeException("Cliente no encontrado con ID: " + cuentaDTO.getCliente());
        }

        Cuenta updatedCuenta = cuentaService.actualizarCuenta(numeroCuenta, cuenta);
        logger.info("Cuenta actualizada: {}", updatedCuenta);
        return updatedCuenta;
    }

    @DeleteMapping("/{numeroCuenta}")
    public void eliminarCuenta(@PathVariable String numeroCuenta) {
        logger.info("Eliminando cuenta con ID: {}", numeroCuenta);
        cuentaService.eliminarCuenta(numeroCuenta);
        logger.info("Cuenta eliminada con éxito");
    }
}
