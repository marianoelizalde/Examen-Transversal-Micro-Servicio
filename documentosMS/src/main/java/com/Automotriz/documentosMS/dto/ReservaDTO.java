package com.Automotriz.documentosMS.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservaDTO {
    private Integer id;
    private String rutCliente;
    private String patente;
    private Integer sucursalId;
    private String fechaInicio; // String 
    private String fechaFin;   // String 
    private String estado;
}
