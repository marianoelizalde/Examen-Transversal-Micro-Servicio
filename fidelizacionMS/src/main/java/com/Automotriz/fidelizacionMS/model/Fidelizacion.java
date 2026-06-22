package com.Automotriz.fidelizacionMS.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "fidelizacion")
public class Fidelizacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "El RUT del cliente es obligatorio")
    @Column(nullable = false, unique = true)
    private String rutCliente;

    @NotNull(message = "Los puntos acumulados son obligatorios")
    @Min(value = 0, message = "Los puntos no pueden ser negativos")
    @Column(nullable = false)
    private Integer puntosAcumulados;

    @NotBlank(message = "El nivel de socio es obligatorio")
    @Pattern(regexp = "BRONCE|PLATA|ORO|PLATINO", message = "El nivel debe ser BRONCE, PLATA, ORO o PLATINO")
    @Column(nullable = false)
    private String nivelSocio;
}
