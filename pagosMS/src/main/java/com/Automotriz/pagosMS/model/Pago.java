package com.Automotriz.pagosMS.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "pago")
public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "El ID de la reserva es obligatorio")
    @Column(nullable = false)
    private Integer reservaId;

    @NotNull(message = "El monto es obligatorio")
    @Positive(message = "El monto debe ser mayor a 0")
    @Column(nullable = false)
    private Double monto;

    @NotBlank(message = "La fecha de pago es obligatoria")
    @Column(nullable = false)
    private String fechaPago;

    @Column(nullable = false)
    private String estadoPago;

    @NotBlank(message = "El método de pago es obligatorio")
    @Column(nullable = false)
    private String metodoPago;
}
