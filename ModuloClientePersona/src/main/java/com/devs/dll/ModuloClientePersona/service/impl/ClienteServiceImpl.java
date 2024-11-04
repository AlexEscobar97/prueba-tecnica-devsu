package com.devs.dll.ModuloClientePersona.service.impl;

import com.devs.dll.ModuloClientePersona.config.KafkaProducerConfig;
import com.devs.dll.ModuloClientePersona.entity.Cliente;
import com.devs.dll.ModuloClientePersona.repository.ClienteRepository;
import com.devs.dll.ModuloClientePersona.service.ClienteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteServiceImpl implements ClienteService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClienteServiceImpl.class);
    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private KafkaProducerConfig kafkaTemplate;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private static final String TOPIC = "clientes";

    @Override
    @Transactional
    public Cliente guardarCliente(Cliente cliente) {
        Cliente savedCliente = clienteRepository.save(cliente);
        try {
            LOGGER.info(String.format("\n ===== Producing message in JSON ===== \n"+savedCliente));
            String mensajeJson = objectMapper.writeValueAsString(savedCliente);
            kafkaTemplate.sendMessage(TOPIC, "Cliente Registrado" + mensajeJson);
        } catch (Exception e) {
            LOGGER.error("Error al serializar el cliente a JSON: {}", e.getMessage());
        }

        return savedCliente;
    }

    @Override
    public List<Cliente> obtenerClientes() {
        return clienteRepository.findAll();
    }

    @Override
    @Transactional
    public Cliente actualizarCliente(Long id, Cliente cliente) {
        cliente.setClienteid(id);
        return clienteRepository.save(cliente);
    }

    @Override
    @Transactional
    public void eliminarCliente(Long id) {
        clienteRepository.deleteById(id);
    }

    @Override
    public Cliente getClienteById(Long id) {
        return clienteRepository.findById(id).orElse(null); // O puedes lanzar una excepción si no lo encuentras
    }
}
