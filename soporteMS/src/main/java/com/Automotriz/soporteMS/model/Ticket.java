package com.Automotriz.soporteMS.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ticket")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "El ID de la reserva es obligatorio")
    @Column(nullable = false)
    private Integer reservaId;

    @NotBlank(message = "El asunto es obligatorio")
    @Size(min = 5, max = 100, message = "El asunto debe tener entre 5 y 100 caracteres")
    @Column(nullable = false)
    private String asunto;

    @NotBlank(message = "La descripción es obligatoria")
    @Size(min = 10, message = "La descripción debe tener al menos 10 caracteres")
    @Column(nullable = false)
    private String descripcion;

    @Column(nullable = false)
    private String estado;

    @Column(nullable = false)
    private String fechaCreacion;
}
