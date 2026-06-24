package com.Automotriz.usuarioMS.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "El RUT es obligatorio")
    @Column(nullable = false, unique = true)
    private String rut;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    @Column(nullable = false)
    private String nombre;

    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "El correo debe tener un formato válido")
    @Column(nullable = false)
    private String correo;

    @NotBlank(message = "El teléfono es obligatorio")
    @Column(nullable = false)
    private String telefono;

    @NotBlank(message = "La dirección es obligatoria")
    @Column(nullable = false)
    private String direccion;

    @NotNull(message = "El tipo es obligatorio")
    @Min(value = 1, message = "El tipo debe ser 1 (CLIENTE) o 2 (EMPLEADO)")
    @Max(value = 2, message = "El tipo debe ser 1 (CLIENTE) o 2 (EMPLEADO)")
    @Column(nullable = false)
    private Integer tipo;

    @NotBlank(message = "La clave única es obligatoria")
    @Column(nullable = false, unique = true)
    private String claveUnica;
}
