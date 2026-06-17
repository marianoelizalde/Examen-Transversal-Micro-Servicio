package com.Automotriz.usuarioMS.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmpleadoDTO {
    private Integer id;
    private String rut;
    private String nombre;
    private String correo;
    private Integer sucursalId;
}
