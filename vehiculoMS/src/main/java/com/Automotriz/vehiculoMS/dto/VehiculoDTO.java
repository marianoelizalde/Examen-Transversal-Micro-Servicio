package com.Automotriz.vehiculoMS.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehiculoDTO {
    private Integer id;
    private String nombre;
    private String modelo;
    private String marca;
    private String patente;
    private String estado;
}
