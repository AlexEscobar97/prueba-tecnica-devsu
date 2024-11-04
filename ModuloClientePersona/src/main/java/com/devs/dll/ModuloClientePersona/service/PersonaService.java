package com.devs.dll.ModuloClientePersona.service;

import com.devs.dll.ModuloClientePersona.entity.Persona;

import java.util.List;

public interface PersonaService {

    Persona guardarPersona(Persona persona);

    List<Persona> obtenerPersonas();

    Persona obtenerPersonaPorId(Long id);

    Persona actualizarPersona(Long id, Persona persona);

    void eliminarPersona(Long id);
}
