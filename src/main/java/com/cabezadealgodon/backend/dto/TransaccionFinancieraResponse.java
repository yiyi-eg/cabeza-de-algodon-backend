package com.cabezadealgodon.backend.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record TransaccionFinancieraResponse(
    Long idTransaccionFinanciera,
    BigDecimal monto,
    String descripcion,
    LocalDate fechaTransaccion,
    String tipo,
    String estadoPago,
    Long internoId,
    String internoNombre,
    String internoApellido,
    String registradoPor,
    LocalDateTime creadoEn,
    LocalDateTime actualizadoEn
) {}