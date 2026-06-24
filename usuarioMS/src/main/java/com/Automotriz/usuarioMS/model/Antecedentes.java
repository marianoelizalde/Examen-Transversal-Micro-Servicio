package com.Automotriz.usuarioMS.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "antecedentes")
public class Antecedentes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "El estado de la licencia es obligatorio")
    @Pattern(regexp = "VIGENTE|SUSPENDIDA|VENCIDA", message = "El estado debe ser VIGENTE, SUSPENDIDA o VENCIDA")
    @Column(nullable = false)
    private String estadoLicencia;

    @NotNull(message = "El registro de multas es obligatorio")
    @Min(value = 0, message = "El registro de multas no puede ser negativo")
    @Column(nullable = false)
    private Integer registroMultas;

    @NotNull(message = "El cliente es obligatorio")
    @OneToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;
}
