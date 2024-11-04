package com.devs.dll.ModuloCuentaMovimiento.controller;

import com.devs.dll.ModuloCuentaMovimiento.dto.MovimientoDTO;
import com.devs.dll.ModuloCuentaMovimiento.entity.Movimientos;
import com.devs.dll.ModuloCuentaMovimiento.exception.SaldoInsuficienteException;
import com.devs.dll.ModuloCuentaMovimiento.service.MovimientoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/movimientos")
public class MovimientoController {

    private static final Logger logger = LoggerFactory.getLogger(MovimientoController.class);

    @Autowired
    private MovimientoService movimientoService;

    @PostMapping
    public MovimientoDTO crearMovimiento(@RequestBody MovimientoDTO movimientoDTO) {
        logger.info("Creando movimiento: {}", movimientoDTO);
        Movimientos movimiento = convertDtoToEntity(movimientoDTO);
        Movimientos savedMovimiento = movimientoService.registrarMovimiento(movimiento);
        logger.info("Movimiento creado con éxito: {}", savedMovimiento);
        return convertEntityToDto(savedMovimiento);
    }

    @GetMapping
    public ResponseEntity<List<Map<String, Object>>> obtenerListadoMovimientos() {
        List<Map<String, Object>> movimientos = movimientoService.obtenerListadoMovimientos();
        return ResponseEntity.ok(movimientos);
    }
    @GetMapping("/listadoNormal")
    public List<MovimientoDTO> obtenerMovimientos() {
        logger.info("Obteniendo todos los movimientos");
        return movimientoService.obtenerMovimientos().stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{numeroCuenta}/{fecha}")
    public MovimientoDTO obtenerMovimientoPorId(@PathVariable String numeroCuenta, @PathVariable String fecha) {
        logger.info("Obteniendo movimiento por ID: {} en fecha: {}", numeroCuenta, fecha);
        Date fechaDate;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
            fechaDate = dateFormat.parse(fecha);
        } catch (ParseException e) {
            logger.error("Error al parsear la fecha: {}", fecha, e);
            return null;
        }

        return movimientoService.obtenerMovimientoPorId(numeroCuenta, fechaDate)
                .map(this::convertEntityToDto)
                .orElse(null);
    }

    @PutMapping("/{numeroCuenta}/{fecha}")
    public MovimientoDTO actualizarMovimiento(@PathVariable String numeroCuenta, @PathVariable String fecha, @RequestBody MovimientoDTO movimientoDTO) {
        logger.info("Actualizando movimiento con número de cuenta: {} y fecha: {}", numeroCuenta, fecha);
        Date fechaDate;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
            fechaDate = dateFormat.parse(fecha);
        } catch (ParseException e) {
            logger.error("Error al parsear la fecha: {}", fecha, e);
            return null;
        }
        Movimientos movimiento = convertDtoToEntity(movimientoDTO);
        movimiento.setNumeroCuenta(numeroCuenta);
        movimiento.setFecha(fechaDate);
        Movimientos updatedMovimiento = movimientoService.actualizarMovimiento(numeroCuenta, fechaDate, movimiento);
        logger.info("Movimiento actualizado: {}", updatedMovimiento);
        return convertEntityToDto(updatedMovimiento);
    }

    @DeleteMapping("/{numeroCuenta}/{fecha}")
    public void eliminarMovimiento(@PathVariable String numeroCuenta, @PathVariable Date fecha) {
        logger.info("Eliminando movimiento con número de cuenta: {} y fecha: {}", numeroCuenta, fecha);
        movimientoService.eliminarMovimiento(numeroCuenta, fecha);
        logger.info("Movimiento eliminado con éxito");
    }

    // Métodos de conversión
    private Movimientos convertDtoToEntity(MovimientoDTO movimientoDTO) {
        Movimientos movimiento = new Movimientos();
        movimiento.setNumeroCuenta(movimientoDTO.getNumeroCuenta());
        movimiento.setFecha(movimientoDTO.getFecha());
        movimiento.setTipoMovimiento(movimientoDTO.getTipoMovimiento());
        movimiento.setValor(movimientoDTO.getValor());
        movimiento.setSaldo(movimientoDTO.getSaldo());
        return movimiento;
    }

    private MovimientoDTO convertEntityToDto(Movimientos movimiento) {
        MovimientoDTO movimientoDTO = new MovimientoDTO();
        movimientoDTO.setNumeroCuenta(movimiento.getNumeroCuenta());
        movimientoDTO.setFecha(movimiento.getFecha());
        movimientoDTO.setTipoMovimiento(movimiento.getTipoMovimiento());
        movimientoDTO.setValor(movimiento.getValor());
        movimientoDTO.setSaldo(movimiento.getSaldo());
        return movimientoDTO;
    }

    @ExceptionHandler(SaldoInsuficienteException.class)
    public ResponseEntity<String> handleSaldoInsuficienteException(SaldoInsuficienteException ex) {
        logger.error("Error de saldo insuficiente: {}", ex.getMessage());
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
