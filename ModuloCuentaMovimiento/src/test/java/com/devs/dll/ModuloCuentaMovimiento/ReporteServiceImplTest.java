package com.devs.dll.ModuloCuentaMovimiento;

import com.devs.dll.ModuloCuentaMovimiento.dto.EstadoCuentaReporteDTO;
import com.devs.dll.ModuloCuentaMovimiento.entity.Cliente;
import com.devs.dll.ModuloCuentaMovimiento.entity.Movimientos;
import com.devs.dll.ModuloCuentaMovimiento.entity.Persona;
import com.devs.dll.ModuloCuentaMovimiento.repository.ClienteRepository;
import com.devs.dll.ModuloCuentaMovimiento.repository.MovimientoRepository;
import com.devs.dll.ModuloCuentaMovimiento.service.impl.ReporteServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ReporteServiceImplTest {

    @InjectMocks
    private ReporteServiceImpl reporteService;

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private MovimientoRepository movimientoRepository;

    private Cliente cliente;

    private Persona persona;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        cliente = new Cliente();
        cliente.setClienteid(1L);
        persona = new Persona();
        persona.setNombre("Juan Pérez");
        persona.setEdad(22);
        persona.setDireccion("direccion");
        cliente.setPersona(persona);
    }

    @Test
    public void testGenerarReporteEstadoCuenta() {
        // Datos de entrada
        Long clienteIdentificacion = 1L;
        Date fechaInicio = new GregorianCalendar(2024, Calendar.NOVEMBER, 1).getTime();
        Date fechaFin = new GregorianCalendar(2024, Calendar.NOVEMBER, 30).getTime();

        // Simulación de los datos devueltos por los repositorios
        when(clienteRepository.findByClienteId(clienteIdentificacion)).thenReturn(Optional.of(cliente));

        List<Map<String, Object>> movimientosData = new ArrayList<>();

        Map<String, Object> movimiento1 = new HashMap<>();
        movimiento1.put("Numero Cuenta", "123456789");
        movimiento1.put("Fecha", new Date());
        movimiento1.put("Tipo", "DEBITO");
        movimiento1.put("Saldo Inicial", 1000.0);
        movimiento1.put("Estado", true);
        movimiento1.put("Movimiento", -200.0);
        movimiento1.put("Saldo Disponible", 800.0);
        movimientosData.add(movimiento1);

        when(movimientoRepository.findMovimientosByClienteAndFecha(cliente.getClienteid(), fechaInicio, fechaFin)).thenReturn(movimientosData);

        // Llamar al método que se va a probar
        List<EstadoCuentaReporteDTO> reportes = reporteService.generarReporteEstadoCuenta(clienteIdentificacion, fechaInicio, fechaFin);

        // Verificar resultados
        assertEquals(1, reportes.size());
        EstadoCuentaReporteDTO reporte = reportes.get(0);
        assertEquals("Juan Pérez", reporte.getCliente());
        assertEquals("123456789", reporte.getNumeroCuenta());
        assertEquals(1000.0, reporte.getSaldoInicial());
        assertEquals(-200.0, reporte.getMovimiento());
        assertEquals(800.0, reporte.getSaldoDisponible());
    }
}
