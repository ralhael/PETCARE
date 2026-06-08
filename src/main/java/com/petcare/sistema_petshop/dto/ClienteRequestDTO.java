package com.petcare.sistema_petshop.dto;

public record ClienteRequestDTO(
        String nome,
        String cpf,
        String telefone,
        String endereco
) {
}
