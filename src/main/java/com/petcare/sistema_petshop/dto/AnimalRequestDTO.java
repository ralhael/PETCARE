package com.petcare.sistema_petshop.dto;

public record AnimalRequestDTO(
        String nome,
        Integer idade,
        Double peso,
        String sexo,
        String alergias,
        String temperamento,
        Long clienteId,
        String raca,
        String porte
) {
}
