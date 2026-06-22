package com.Automotriz.tarifasMS.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tarifa")
public class Tarifa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "El ID del vehículo es obligatorio")
    @Column(nullable = false)
    private Integer vehiculoId;

    @NotNull(message = "El precio por día es obligatorio")
    @Positive(message = "El precio debe ser mayor a 0")
    @Column(nullable = false)
    private Double precioDia;

    @NotBlank(message = "La temporada es obligatoria")
    @Pattern(regexp = "BAJA|NORMAL|ALTA", message = "La temporada debe ser BAJA, NORMAL o ALTA")
    @Column(nullable = false)
    private String temporada;

    @NotBlank(message = "El estado es obligatorio")
    @Pattern(regexp = "ACTIVO|INACTIVO", message = "El estado debe ser ACTIVO o INACTIVO")
    @Column(nullable = false)
    private String estado;
}
