package com.petcare.sistema_petshop.dto;

public record FuncionarioRequestDTO(

        String nome,
        String telefone,
        String cpf,
        String cargo,
        String especialidade
) {
}