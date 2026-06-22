package com.Automotriz.documentosMS.dto;

import lombok.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContratoDTO {
    private Integer id;
    private Integer reservaId;
    private LocalDate fechaEmision;
    private String estado;
}
