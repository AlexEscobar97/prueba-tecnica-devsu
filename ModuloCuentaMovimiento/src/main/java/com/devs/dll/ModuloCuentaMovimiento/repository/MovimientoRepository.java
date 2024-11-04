package com.devs.dll.ModuloCuentaMovimiento.repository;

import com.devs.dll.ModuloCuentaMovimiento.entity.Cliente;
import com.devs.dll.ModuloCuentaMovimiento.entity.Movimientos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface MovimientoRepository extends JpaRepository<Movimientos, Long> {

    Optional<Movimientos> findByNumeroCuentaAndFecha(String numeroCuenta, Date fecha);
    void deleteByNumeroCuentaAndFecha(String numeroCuenta, Date fecha);
    Movimientos findTopByNumeroCuentaOrderByFechaDesc(String numeroCuenta);

    @Query(value = "SELECT " +
            "m.fecha AS Fecha, " +
            "p.nombre AS Cliente, " +
            "c.numero_cuenta AS `Numero Cuenta`, " +
            "c.tipo_cuenta AS Tipo, " +
            "c.saldo_inicial AS `Saldo Inicial`, " +
            "c.estado AS Estado, " +
            "m.valor AS Movimiento, " +
            "(c.saldo_inicial + m.valor) AS `Saldo Disponible` " +
            "FROM movimientos m " +
            "JOIN cuenta c ON m.numero_cuenta = c.numero_cuenta " +
            "JOIN cliente cl ON c.clienteid = cl.clienteid " +
            "JOIN persona p ON cl.id_persona = p.id " +
            "WHERE cl.clienteid = :clienteId AND " +
            "m.fecha BETWEEN :fechaInicio AND :fechaFin",
            nativeQuery = true)
    List<Map<String, Object>> findMovimientosByClienteAndFecha(
            @Param("clienteId") Long clienteId,
            @Param("fechaInicio") Date fechaInicio,
            @Param("fechaFin") Date fechaFin);

    @Query(value = "SELECT m.fecha AS fecha, p.nombre AS cliente, c.numero_cuenta AS numero, " +
            "c.tipo_cuenta AS tipo, c.saldo_inicial AS saldoInicial, c.estado AS estado, m.valor AS movimiento, " +
            "m.saldo AS saldoDisponible, c.numero_cuenta AS cuenta " +
            "FROM movimientos m " +
            "JOIN cuenta c ON m.numero_cuenta = c.numero_cuenta " +
            "JOIN cliente cl ON c.clienteid = cl.clienteid " +
            "JOIN persona p ON cl.id_persona = p.id " +
            "ORDER BY m.fecha DESC", nativeQuery = true)
    List<Map<String, Object>> obtenerListadoMovimientos();

}
