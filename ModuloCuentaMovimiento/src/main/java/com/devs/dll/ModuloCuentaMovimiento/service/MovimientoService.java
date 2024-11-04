package com.devs.dll.ModuloCuentaMovimiento.service;

import com.devs.dll.ModuloCuentaMovimiento.dto.EstadoCuentaReporteDTO;
import com.devs.dll.ModuloCuentaMovimiento.entity.Movimientos;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface MovimientoService {

    Movimientos registrarMovimiento(Movimientos movimiento);

    Movimientos guardarMovimiento(Movimientos movimiento);

    List<Movimientos> obtenerMovimientos();

    Optional<Movimientos> obtenerMovimientoPorId(String numeroCuenta, Date fecha);

    Movimientos actualizarMovimiento(String numeroCuenta, Date fecha, Movimientos movimiento);

    void eliminarMovimiento(String numeroCuenta, Date fecha);

    List<Map<String, Object>> obtenerListadoMovimientos();

}
