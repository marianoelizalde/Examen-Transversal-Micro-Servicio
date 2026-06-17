package com.Automotriz.notificacionesMS.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "notificacion")
public class Notificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "El tipo es obligatorio")
    @Pattern(regexp = "EMAIL|SMS|PUSH", message = "El tipo debe ser EMAIL, SMS o PUSH")
    @Column(nullable = false)
    private String tipo;

    @NotNull(message = "El ID de la reserva es obligatorio")
    @Column(nullable = false)
    private Integer reservaId;

    @NotBlank(message = "El mensaje es obligatorio")
    @Size(min = 5, message = "El mensaje debe tener al menos 5 caracteres")
    @Column(nullable = false)
    private String mensaje;

    @Column(nullable = false)
    private String fechaNotificacion;

    @Column(nullable = false)
    private String estado;
}
