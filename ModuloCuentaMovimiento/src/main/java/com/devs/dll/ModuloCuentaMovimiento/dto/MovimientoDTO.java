package com.devs.dll.ModuloCuentaMovimiento.dto;

import lombok.Data;

import java.util.Date;

@Data
public class MovimientoDTO {
    private String numeroCuenta;
    private Date fecha;
    private String tipoMovimiento;
    private Double valor;
    private Double saldo;
}