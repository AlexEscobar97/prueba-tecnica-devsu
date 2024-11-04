package com.devs.dll.ModuloCuentaMovimiento.dto;

import lombok.Data;

import java.util.List;

@Data
public class EstadoCuentaReporteDTO {

    private String fecha;
    private String cliente;
    private String numeroCuenta;
    private String tipo;
    private Double saldoInicial;
    private Boolean estado;
    private Double movimiento;
    private Double saldoDisponible;
}
