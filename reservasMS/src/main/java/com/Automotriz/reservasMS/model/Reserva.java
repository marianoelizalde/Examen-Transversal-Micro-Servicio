package com.Automotriz.reservasMS.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "reserva")
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "El RUT del cliente es obligatorio")
    @Column(nullable = false)
    private String rutCliente;

    @NotBlank(message = "La patente del vehículo es obligatoria")
    @Column(nullable = false)
    private String patente;

    @NotNull(message = "La sucursal es obligatoria")
    @Column(nullable = false)
    private Integer sucursalId;

    @NotBlank(message = "La fecha de inicio es obligatoria")
    @Column(nullable = false)
    private String fechaInicio;

    @NotBlank(message = "La fecha de fin es obligatoria")
    @Column(nullable = false)
    private String fechaFin;

    @Column(nullable = false)
    private String estado;
}
