package com.devs.dll.ModuloCuentaMovimiento.service.impl;

import com.devs.dll.ModuloCuentaMovimiento.dto.ClienteEvent;
import com.devs.dll.ModuloCuentaMovimiento.entity.Cliente;
import com.devs.dll.ModuloCuentaMovimiento.entity.Movimientos;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class GenericConsumerServiceImpl {

    private static final Logger logger = LoggerFactory.getLogger(GenericConsumerServiceImpl.class);
    private final ObjectMapper objectMapper = new ObjectMapper();
    @KafkaListener(topics = "clientes", groupId = "grupo_cliente")
    public void consumirEventoCliente(String message) {
        try {
            //ClienteEvent event = objectMapper.readValue(message, ClienteEvent.class);
            logger.info("Cliente recibido: {}", message);
        } catch (Exception e) {
            logger.error("Error deserializando mensaje: {}", e.getMessage());
        }
    }
}
