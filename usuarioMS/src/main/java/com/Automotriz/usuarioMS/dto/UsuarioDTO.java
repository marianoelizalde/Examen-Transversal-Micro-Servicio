package com.Automotriz.usuarioMS.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTO {
    private Integer id;
    private String rut;
    private String nombre;
    private String correo;
    private String telefono;
    private Integer tipo;
}
