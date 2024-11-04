package com.devs.dll.ModuloCuentaMovimiento.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Entity
@Table(name = "movimientos")
@Data
@ToString
public class Movimientos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date fecha;
    private String tipoMovimiento;
    private Double valor;
    private Double saldo;

    @Column(name = "numero_cuenta")
    private String numeroCuenta;

    @ManyToOne
    @JoinColumn(name = "numero_cuenta", referencedColumnName = "numero_cuenta", insertable = false, updatable = false)
    private Cuenta cuenta;

}
