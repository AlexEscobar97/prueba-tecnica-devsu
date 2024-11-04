package com.devs.dll.ModuloClientePersona.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@DiscriminatorValue("CLIENTE")
@Table(name = "cliente")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
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
