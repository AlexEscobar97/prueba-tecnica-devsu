package com.devs.dll.ModuloCuentaMovimiento.dto;

import lombok.Data;

@Data
public class CuentaDTO {
    private String numeroCuenta;
    private String tipoCuenta;
    private Double saldoInicial;
    private Boolean estado;
    private Long cliente;
}
