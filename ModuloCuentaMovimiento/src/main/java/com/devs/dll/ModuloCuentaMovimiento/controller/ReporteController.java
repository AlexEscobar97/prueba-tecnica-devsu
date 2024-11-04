package com.devs.dll.ModuloCuentaMovimiento.controller;

import com.devs.dll.ModuloCuentaMovimiento.dto.EstadoCuentaReporteDTO;
import com.devs.dll.ModuloCuentaMovimiento.service.ReporteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/reportes")
public class ReporteController {

    @Autowired
    private ReporteService reporteService;

    @GetMapping
    public List<EstadoCuentaReporteDTO> generarReporteEstadoCuenta(
            @RequestParam("cliente") Long cliente,
            @RequestParam("fechaInicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fechaInicio,
            @RequestParam("fechaFin") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fechaFin) {
        return reporteService.generarReporteEstadoCuenta(cliente, fechaInicio, fechaFin);
    }
}
