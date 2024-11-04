package com.devs.dll.ModuloCuentaMovimiento;

import com.devs.dll.ModuloCuentaMovimiento.entity.Movimientos;
import com.devs.dll.ModuloCuentaMovimiento.exception.SaldoInsuficienteException;
import com.devs.dll.ModuloCuentaMovimiento.repository.MovimientoRepository;
import com.devs.dll.ModuloCuentaMovimiento.service.impl.MovimientoServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.Optional;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MovimientoServiceImplTest {

    @InjectMocks
    private MovimientoServiceImpl movimientoService;

    @Mock
    private MovimientoRepository movimientoRepository;

    @Mock
    private KafkaTemplate<String, Movimientos> kafkaTemplate;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void registrarMovimiento_conSaldoSuficiente_debeRegistrarMovimiento() {
        // Arrange
        Movimientos movimiento = new Movimientos();
        movimiento.setNumeroCuenta("123");
        movimiento.setValor(500.0);

        Movimientos ultimoMovimiento = new Movimientos();
        ultimoMovimiento.setSaldo(1000.0);

        when(movimientoRepository.findTopByNumeroCuentaOrderByFechaDesc(movimiento.getNumeroCuenta()))
                .thenReturn(ultimoMovimiento);
        when(movimientoRepository.save(any(Movimientos.class))).thenReturn(movimiento);

        // Act
        Movimientos resultado = movimientoService.registrarMovimiento(movimiento);

        // Assert
        assertNotNull(resultado);
        assertEquals(1500.0, resultado.getSaldo());
        verify(movimientoRepository, times(1)).save(movimiento);
    }

    @Test
    void registrarMovimiento_conSaldoInsuficiente_debeLanzarSaldoInsuficienteException() {
        // Arrange
        Movimientos movimiento = new Movimientos();
        movimiento.setNumeroCuenta("123");
        movimiento.setValor(-1500.0);

        Movimientos ultimoMovimiento = new Movimientos();
        ultimoMovimiento.setSaldo(1000.0);

        when(movimientoRepository.findTopByNumeroCuentaOrderByFechaDesc(movimiento.getNumeroCuenta()))
                .thenReturn(ultimoMovimiento);

        // Act & Assert
        assertThrows(SaldoInsuficienteException.class, () -> movimientoService.registrarMovimiento(movimiento));
    }
}

