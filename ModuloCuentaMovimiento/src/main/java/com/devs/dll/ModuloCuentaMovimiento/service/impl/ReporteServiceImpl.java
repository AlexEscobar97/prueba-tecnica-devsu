package com.devs.dll.ModuloCuentaMovimiento.service.impl;

import com.devs.dll.ModuloCuentaMovimiento.dto.EstadoCuentaReporteDTO;
import com.devs.dll.ModuloCuentaMovimiento.dto.MovimientoDetalleDTO;
import com.devs.dll.ModuloCuentaMovimiento.entity.Cliente;
import com.devs.dll.ModuloCuentaMovimiento.entity.Movimientos;
import com.devs.dll.ModuloCuentaMovimiento.repository.ClienteRepository;
import com.devs.dll.ModuloCuentaMovimiento.repository.MovimientoRepository;
import com.devs.dll.ModuloCuentaMovimiento.service.ReporteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReporteServiceImpl implements ReporteService{

    @Autowired
    private MovimientoRepository movimientoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public List<EstadoCuentaReporteDTO> generarReporteEstadoCuenta(Long clienteIdentificacion, Date fechaInicio, Date fechaFin) {
        // Buscar el objeto Cliente
        Cliente cliente = clienteRepository.findByClienteId(clienteIdentificacion)
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado"));

        // Obtener movimientos por cliente en el rango de fechas
        List<Map<String, Object>> movimientosData = movimientoRepository.findMovimientosByClienteAndFecha(cliente.getClienteid(), fechaInicio, fechaFin);

        // Agrupar los movimientos por cuenta y mapearlos al DTO
        return movimientosData.stream()
                .collect(Collectors.groupingBy(data -> data.get("Numero Cuenta")))
                .entrySet()
                .stream()
                .flatMap(entry -> {
                    String numeroCuenta = entry.getKey().toString();

                    return entry.getValue().stream().map(data -> {
                        EstadoCuentaReporteDTO reporte = new EstadoCuentaReporteDTO();

                        // Establecer todos los campos del DTO
                        reporte.setNumeroCuenta(numeroCuenta);
                        reporte.setFecha(data.get("Fecha").toString());
                        reporte.setCliente(cliente.getPersona().getNombre());
                        reporte.setTipo(data.get("Tipo").toString());
                        reporte.setSaldoInicial((Double) data.get("Saldo Inicial"));
                        reporte.setEstado((Boolean) data.get("Estado"));
                        reporte.setMovimiento((Double) data.get("Movimiento"));
                        reporte.setSaldoDisponible((Double) data.get("Saldo Disponible"));

                        return reporte;
                    });
                })
                .collect(Collectors.toList());
    }

}
