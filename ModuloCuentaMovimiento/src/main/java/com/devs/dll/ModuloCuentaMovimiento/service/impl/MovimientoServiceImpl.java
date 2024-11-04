package com.devs.dll.ModuloCuentaMovimiento.service.impl;

import com.devs.dll.ModuloCuentaMovimiento.controller.MovimientoController;
import com.devs.dll.ModuloCuentaMovimiento.dto.EstadoCuentaReporteDTO;
import com.devs.dll.ModuloCuentaMovimiento.dto.MovimientoDetalleDTO;
import com.devs.dll.ModuloCuentaMovimiento.entity.Cuenta;
import com.devs.dll.ModuloCuentaMovimiento.entity.Movimientos;
import com.devs.dll.ModuloCuentaMovimiento.exception.SaldoInsuficienteException;
import com.devs.dll.ModuloCuentaMovimiento.repository.CuentaRepository;
import com.devs.dll.ModuloCuentaMovimiento.repository.MovimientoRepository;
import com.devs.dll.ModuloCuentaMovimiento.service.MovimientoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MovimientoServiceImpl implements MovimientoService {
    private static final Logger logger = LoggerFactory.getLogger(MovimientoServiceImpl.class);
    @Autowired
    private MovimientoRepository movimientoRepository;

    @Autowired
    private CuentaRepository cuentaRepository;

    @Autowired
    private KafkaTemplate<String, Movimientos> kafkaTemplate;

    private static final String TOPIC = "movimientos";

    @Override
    public Movimientos registrarMovimiento(Movimientos movimiento) {
        // Obtiene el último saldo registrado para esta cuenta
        Movimientos ultimoMovimiento = movimientoRepository.findTopByNumeroCuentaOrderByFechaDesc(movimiento.getNumeroCuenta());

        double saldoActual;

        if (ultimoMovimiento != null && ultimoMovimiento.getSaldo() != null) {
            saldoActual = ultimoMovimiento.getSaldo();
        } else {
            saldoActual = cuentaRepository.findCuentaByNumeroCuenta(movimiento.getNumeroCuenta())
                    .map(Cuenta::getSaldoInicial)
                    .orElse(0.00);
        }

        logger.info("Ultimo movimiento {}", ultimoMovimiento != null ? ultimoMovimiento.toString() : " [Dato no encontrado]");
        logger.info("Saldo actual {}", saldoActual);

        if (movimiento.getValor() + saldoActual < 0) {
            throw new SaldoInsuficienteException("Saldo no disponible");
        }

        double nuevoSaldo = saldoActual + movimiento.getValor();
        movimiento.setSaldo(nuevoSaldo);
        logger.info("Guardar Inf {}", movimiento.toString());

        Movimientos savedMovimiento = movimientoRepository.save(movimiento);
        // kafkaTemplate.send(TOPIC, savedMovimiento); // Envía el movimiento a Kafka si es necesario
        return savedMovimiento;
    }

    @Override
    public Movimientos guardarMovimiento(Movimientos movimiento) {
        return movimientoRepository.save(movimiento);
    }

    @Override
    public List<Movimientos> obtenerMovimientos() {
        return movimientoRepository.findAll();
    }

    @Override
    public Optional<Movimientos> obtenerMovimientoPorId(String numeroCuenta, Date fecha) {
        return movimientoRepository.findByNumeroCuentaAndFecha(numeroCuenta, fecha);
    }

    @Override
    public Movimientos actualizarMovimiento(String numeroCuenta, Date fecha, Movimientos movimiento) {
        Optional<Movimientos> existingMovimientoOpt = movimientoRepository.findByNumeroCuentaAndFecha(numeroCuenta, fecha);

        if (existingMovimientoOpt.isPresent()) {
            Movimientos existingMovimiento = existingMovimientoOpt.get();
            existingMovimiento.setTipoMovimiento(movimiento.getTipoMovimiento());
            existingMovimiento.setValor(movimiento.getValor());
            existingMovimiento.setSaldo(movimiento.getSaldo());
            return movimientoRepository.save(existingMovimiento);
        } else {

            return null;
        }
    }

    @Override
    public void eliminarMovimiento(String numeroCuenta, Date fecha) {
        Optional<Movimientos> existingMovimientoOpt = movimientoRepository.findByNumeroCuentaAndFecha(numeroCuenta, fecha);
        existingMovimientoOpt.ifPresent(movimientoRepository::delete);
    }

    @Override
    public List<Map<String, Object>> obtenerListadoMovimientos() {
        return movimientoRepository.obtenerListadoMovimientos();
    }
}
