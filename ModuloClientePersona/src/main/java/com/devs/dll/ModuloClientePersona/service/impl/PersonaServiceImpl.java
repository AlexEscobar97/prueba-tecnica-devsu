package com.devs.dll.ModuloClientePersona.service.impl;

import com.devs.dll.ModuloClientePersona.entity.Persona;
import com.devs.dll.ModuloClientePersona.repository.PersonaRepository;
import com.devs.dll.ModuloClientePersona.service.PersonaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonaServiceImpl implements PersonaService {

    @Autowired
    private PersonaRepository personaRepository;

    @Override
    public Persona guardarPersona(Persona persona) {
        return personaRepository.save(persona);
    }

    @Override
    public List<Persona> obtenerPersonas() {
        return personaRepository.findAll();
    }

    @Override
    public Persona obtenerPersonaPorId(Long id) {
        Optional<Persona> persona = personaRepository.findById(id);
        return persona.orElseThrow(() -> new RuntimeException("Persona no encontrada con el ID: " + id));
    }

    @Override
    public Persona actualizarPersona(Long id, Persona persona) {
        persona.setId(id);
        return personaRepository.save(persona);
    }

    @Override
    public void eliminarPersona(Long id) {
        personaRepository.deleteById(id);
    }
}
