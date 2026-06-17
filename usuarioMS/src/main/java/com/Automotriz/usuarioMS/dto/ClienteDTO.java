package com.Automotriz.usuarioMS.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteDTO {
    private Integer id;
    private String rut;
    private String nombre;
    private String correo;
    private String tipoCliente;
}
