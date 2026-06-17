package com.Automotriz.pagosMS.dto;

import lombok.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PagoDTO {
    private Integer id;
    private Integer reservaId;
    private Double monto;
    private LocalDate fechaPago;
    private String estadoPago;
    private String metodoPago;
}
