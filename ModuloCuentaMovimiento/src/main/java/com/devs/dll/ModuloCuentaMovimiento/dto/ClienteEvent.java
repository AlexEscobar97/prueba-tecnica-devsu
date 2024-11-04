package com.devs.dll.ModuloCuentaMovimiento.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClienteEvent implements Serializable {
    private Long id;
    private String nombre;
    private String email;
    private String telefono;
}
