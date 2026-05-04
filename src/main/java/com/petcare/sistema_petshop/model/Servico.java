package com.petcare.sistema_petshop.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data                               // faz o papel de ter todos os metodos gets e sets ... ( chatos )
@Entity                             // anotacao faz entender que isso é uma entidade ( uma tabela no banco de dados)
public class Servico {

    @Id                             // faz entender que isso é um id
    @GeneratedValue(strategy = GenerationType.IDENTITY)     // faz o postgre entender que ele vai criar o id automaticamente com o metodo IDENTITY dele com o sereal
    private Long id;

    private String descricao;
    private Double preco;

}
