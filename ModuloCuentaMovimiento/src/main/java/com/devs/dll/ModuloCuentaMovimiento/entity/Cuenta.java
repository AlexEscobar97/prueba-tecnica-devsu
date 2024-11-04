package com.devs.dll.ModuloCuentaMovimiento.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "cuenta")
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Cuenta {
    @Id
    @Column(name = "numero_cuenta")
    private String numeroCuenta;

    private String tipoCuenta;
    private Double saldoInicial;
    private Boolean estado;

    @ManyToOne
    @JoinColumn(name = "clienteid", referencedColumnName = "clienteid")
    private Cliente cliente;
}
