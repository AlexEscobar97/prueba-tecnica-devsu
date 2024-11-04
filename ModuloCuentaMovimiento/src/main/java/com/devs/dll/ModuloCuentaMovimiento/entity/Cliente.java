package com.devs.dll.ModuloCuentaMovimiento.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@DiscriminatorValue("CLIENTE")
@Table(name = "cliente")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long clienteid;

    private String password;
    private boolean estado;

    @OneToOne
    @JoinColumn(name = "id_persona", referencedColumnName = "id", unique = true)
    private Persona persona;

    // Constructor
    public Cliente(String password, boolean estado, Persona persona) {
        this.password = password;
        this.estado = estado;
        this.persona = persona;
    }

}
