package com.devs.dll.ModuloClientePersona.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClienteDTO {
    private String password;
    private boolean estado;
    private PersonaDTO persona;

    @Getter
    @Setter
    public static class PersonaDTO {
        private String nombre;
        private String genero;
        private int edad;
        private String identificacion;
        private String direccion;
        private String telefono;
    }
}
