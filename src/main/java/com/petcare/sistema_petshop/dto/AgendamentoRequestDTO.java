package com.petcare.sistema_petshop.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record AgendamentoRequestDTO(
        Long clienteId,
        Long animalId,
        Long funcionarioId,
        LocalDate data ,
        LocalTime hora ,
        String servico,
        String observacoes) {
}
