package com.petcare.sistema_petshop.dto;

public record AnimalRequestDTO(
        String nome,
        String tipo,
        String raca,
        Integer idade,
        Double peso,
        String porte,
        String sexo,
        String temperamento
) {
}
