package com.devs.dll.ModuloClientePersona.repository;

import com.devs.dll.ModuloClientePersona.entity.Persona;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonaRepository extends JpaRepository<Persona, Long> {
}
