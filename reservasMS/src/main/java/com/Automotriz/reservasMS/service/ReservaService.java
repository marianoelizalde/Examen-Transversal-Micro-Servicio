package com.Automotriz.reservasMS.service;

import com.Automotriz.reservasMS.client.SucursalClient;
import com.Automotriz.reservasMS.client.UsuarioClient;
import com.Automotriz.reservasMS.client.VehiculoClient;
import com.Automotriz.reservasMS.dto.*;
import com.Automotriz.reservasMS.model.Reserva;
import com.Automotriz.reservasMS.repository.ReservaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
public class ReservaService {

    @Autowired private ReservaRepository reservaRepository;
    @Autowired private UsuarioClient usuarioClient;
    @Autowired private VehiculoClient vehiculoClient;
    @Autowired private SucursalClient sucursalClient;

    public List<Reserva> listar() {
        log.info("Listando todas las reservas");
        return reservaRepository.findAll();
    }

    public Reserva buscarPorId(Integer id) {
        log.info("Buscando reserva con ID: {}", id);
        return reservaRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Reserva con ID {} no encontrada", id);
                    return new RuntimeException("Reserva no encontrada");
                });
    }

    public List<Reserva> buscarPorCliente(String rutCliente) {
        log.info("Buscando reservas del cliente: {}", rutCliente);
        return reservaRepository.findByRutCliente(rutCliente);
    }

    public List<Reserva> buscarPorEstado(String estado) {
        log.info("Buscando reservas con estado: {}", estado);
        return reservaRepository.findByEstado(estado);
    }

    public Reserva guardar(Reserva reserva) {

        // REGLA 1: Validar que fechaFin sea posterior a fechaInicio
        LocalDate inicio = LocalDate.parse(reserva.getFechaInicio());
        LocalDate fin = LocalDate.parse(reserva.getFechaFin());
        if (!fin.isAfter(inicio)) {
            log.error("Fecha inválida: fechaFin {} debe ser posterior a fechaInicio {}", fin, inicio);
            throw new RuntimeException("La fecha de fin debe ser posterior a la fecha de inicio");
        }

        // REGLA 2: Verificar que el vehículo exista y esté DISPONIBLE
        log.info("Verificando disponibilidad del vehículo con patente: {}", reserva.getPatente());
        VehiculoDTO vehiculo = vehiculoClient.obtenerVehiculoPorPatente(reserva.getPatente());
        if (!vehiculo.getEstado().equals("DISPONIBLE")) {
            log.error("Vehículo {} no disponible. Estado actual: {}", reserva.getPatente(), vehiculo.getEstado());
            throw new RuntimeException("El vehículo con patente " + reserva.getPatente()
                    + " no está disponible. Estado actual: " + vehiculo.getEstado());
        }

        // REGLA 3: Verificar que no exista otra reserva ACTIVA para el mismo vehículo
        if (reservaRepository.existsByPatenteAndEstado(reserva.getPatente(), "ACTIVA")) {
            log.error("El vehículo {} ya tiene una reserva ACTIVA", reserva.getPatente());
            throw new RuntimeException("El vehículo con patente " + reserva.getPatente()
                    + " ya tiene una reserva activa");
        }

        // REGLA 4: Verificar antecedentes del cliente (licencia vigente y sin exceso de multas)
        log.info("Verificando antecedentes del cliente: {}", reserva.getRutCliente());
        try {
            UsuarioDTO usuario = usuarioClient.obtenerUsuarioPorRut(reserva.getRutCliente());
            ClienteDTO cliente = usuarioClient.obtenerClientePorUsuarioId(usuario.getId());
            AntecedentesDTO antecedentes = usuarioClient.obtenerAntecedentesPorClienteId(cliente.getId());

            if (!antecedentes.getEstadoLicencia().equals("VIGENTE")) {
                log.error("Cliente {} no puede arrendar. Licencia: {}", reserva.getRutCliente(), antecedentes.getEstadoLicencia());
                throw new RuntimeException("El cliente no puede arrendar. Licencia en estado: "
                        + antecedentes.getEstadoLicencia());
            }

            if (antecedentes.getRegistroMultas() >= 3) {
                log.error("Cliente {} no puede arrendar. Tiene {} multas", reserva.getRutCliente(), antecedentes.getRegistroMultas());
                throw new RuntimeException("El cliente no puede arrendar. Tiene "
                        + antecedentes.getRegistroMultas() + " multas registradas (máximo permitido: 2)");
            }

            log.info("Antecedentes del cliente {} validados correctamente. Licencia: {}, Multas: {}",
                    reserva.getRutCliente(), antecedentes.getEstadoLicencia(), antecedentes.getRegistroMultas());

        } catch (RuntimeException e) {
            // Si el error es de antecedentes, propagarlo
            if (e.getMessage().contains("arrendar")) {
                throw e;
            }
            // Si el error es que no se encontraron antecedentes, permitir continuar con advertencia
            log.warn("No se pudieron verificar antecedentes del cliente: {}. Se permite continuar.", reserva.getRutCliente());
        }

        // Todas las validaciones pasaron: crear la reserva en estado PENDIENTE
        reserva.setEstado("PENDIENTE");
        Reserva resultado = reservaRepository.save(reserva);
        log.info("Reserva creada con ID: {} para cliente: {} vehículo: {}",
                resultado.getId(), resultado.getRutCliente(), resultado.getPatente());
        return resultado;
    }

    public Reserva actualizar(Integer id, Reserva datos) {
        log.info("Actualizando reserva con ID: {}", id);
        Reserva reserva = buscarPorId(id);
        reserva.setRutCliente(datos.getRutCliente());
        reserva.setPatente(datos.getPatente());
        reserva.setSucursalId(datos.getSucursalId());
        reserva.setFechaInicio(datos.getFechaInicio());
        reserva.setFechaFin(datos.getFechaFin());
        reserva.setEstado(datos.getEstado());
        Reserva resultado = reservaRepository.save(reserva);
        log.info("Reserva {} actualizada correctamente", id);
        return resultado;
    }

    public void eliminar(Integer id) {
        if (!reservaRepository.existsById(id)) {
            log.error("No se puede eliminar. Reserva con ID {} no existe", id);
            throw new RuntimeException("Reserva no existe");
        }
        reservaRepository.deleteById(id);
        log.info("Reserva con ID {} eliminada correctamente", id);
    }

    public ReservaDetalleDTO obtenerDetalle(Integer id) {
        log.info("Obteniendo detalle de reserva con ID: {}", id);
        Reserva reserva = buscarPorId(id);
        UsuarioDTO usuario = usuarioClient.obtenerUsuarioPorRut(reserva.getRutCliente());
        VehiculoDTO vehiculo = vehiculoClient.obtenerVehiculoPorPatente(reserva.getPatente());
        SucursalDTO sucursal = sucursalClient.obtenerSucursal(reserva.getSucursalId());
        log.info("Detalle de reserva {} obtenido correctamente", id);
        return new ReservaDetalleDTO(reserva.getId(), reserva.getEstado(),
                reserva.getFechaInicio(), reserva.getFechaFin(), usuario, vehiculo, sucursal);
    }
}
