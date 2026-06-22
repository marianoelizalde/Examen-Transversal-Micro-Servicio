package com.Automotriz.documentosMS.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "participante")
public class Participante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "El RUT es obligatorio")
    @Column(nullable = false)
    private String rut;

    @NotBlank(message = "El nombre es obligatorio")
    @Column(nullable = false)
    private String nombre;

    @NotBlank(message = "El rol es obligatorio")
    @Pattern(regexp = "ARRENDATARIO|EMPLEADO", message = "El rol debe ser ARRENDATARIO o EMPLEADO")
    @Column(nullable = false)
    private String rol;

    @NotNull(message = "El campo firmado es obligatorio")
    @Column(nullable = false)
    private Boolean firmado;

    @NotNull(message = "El contrato es obligatorio")
    @ManyToOne
    @JoinColumn(name = "contrato_id", nullable = false)
    private Contrato contrato;
}
