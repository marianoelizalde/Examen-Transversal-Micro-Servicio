package com.Automotriz.pagosMS.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservaDTO {
    private Integer id;
    private String rutCliente;
    private String patente;
    private Integer sucursalId;
    private String fechaInicio; // String en vez de LocalDate
    private String fechaFin;   // String en vez de LocalDate
    private String estado;
}
