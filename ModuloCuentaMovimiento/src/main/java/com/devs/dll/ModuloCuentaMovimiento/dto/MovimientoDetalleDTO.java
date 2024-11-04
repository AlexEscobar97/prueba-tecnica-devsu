package com.devs.dll.ModuloCuentaMovimiento.dto;

import lombok.Data;

import java.util.Date;

@Data
public class MovimientoDetalleDTO {

    private Date fecha;
    private String tipoMovimiento;
    private double valor;
    private double saldo;
}
