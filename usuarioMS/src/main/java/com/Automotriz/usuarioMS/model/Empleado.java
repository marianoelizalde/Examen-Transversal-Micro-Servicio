package com.Automotriz.usuarioMS.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "empleado")
public class Empleado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String descripcion;

    @NotNull(message = "La sucursal es obligatoria")
    @Column(name = "sucursal_id", nullable = false)
    private Integer sucursalId;

    @NotBlank(message = "La información bancaria es obligatoria")
    @Column(nullable = false)
    private String infoBancaria;

    @NotNull(message = "El usuario es obligatorio")
    @OneToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;
}
