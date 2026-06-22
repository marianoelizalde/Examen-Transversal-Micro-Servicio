package com.Automotriz.documentosMS.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "contrato")
public class Contrato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "El ID de la reserva es obligatorio")
    @Column(nullable = false)
    private Integer reservaId;

    @NotBlank(message = "La fecha de emisión es obligatoria")
    @Column(nullable = false)
    private String fechaEmision;

    @Column(nullable = false)
    private String estado;

    @NotBlank(message = "Las cláusulas son obligatorias")
    @Size(min = 10, message = "Las cláusulas deben tener al menos 10 caracteres")
    @Column(nullable = false)
    private String clausulas;
}
