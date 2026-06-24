package com.Automotriz.reservasMS.dto;

import lombok.*;

@Data @NoArgsConstructor @AllArgsConstructor
public class SucursalDTO {
    private Integer id;
    private String nombre;
    private String direccion;
    private String comuna;
}
