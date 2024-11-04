package com.devs.dll.ModuloCuentaMovimiento.service;

import com.devs.dll.ModuloCuentaMovimiento.dto.EstadoCuentaReporteDTO;
import com.devs.dll.ModuloCuentaMovimiento.entity.Cliente;

import java.util.Date;
import java.util.List;

public interface ReporteService {

    List<EstadoCuentaReporteDTO> generarReporteEstadoCuenta(Long clienteIdentificacion, Date fechaInicio, Date fechaFin);
}
