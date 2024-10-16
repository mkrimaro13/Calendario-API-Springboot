package com.calendar.calendar.domain.DTOs;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor // Genera un constructor sin argumentos
@AllArgsConstructor // Genera un constructor con todos los campos
public class FestivoDto {
    // DTO -> Data Transfer Object, objeto de transferencia de información
    private LocalDate fecha;
}
