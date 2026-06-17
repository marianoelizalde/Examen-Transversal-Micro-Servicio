package com.Automotriz.sucursalMS.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sucursal")
public class Sucursal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "El nombre es obligatorio")
    @Column(nullable = false)
    private String nombre;

    @NotBlank(message = "La dirección es obligatoria")
    @Column(nullable = false)
    private String direccion;

    @NotBlank(message = "La comuna es obligatoria")
    @Column(nullable = false)
    private String comuna;

    @Min(value = 0, message = "La cantidad de empleados no puede ser negativa")
    @Column
    private Integer cantidadEmpleados;
}
