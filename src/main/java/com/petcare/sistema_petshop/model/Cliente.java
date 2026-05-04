package com.petcare.sistema_petshop.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data                                       // anotacao que faz pegar todos os metodos chatos ( gets sets toString etc )
@Entity                                     // anotacao que faz entender que é uma entidade ( uma tabela no banco de dados )
@EqualsAndHashCode(callSuper = true)        // faz com que pegue os metodos e atributos e compare para saber se sao iguais
public class Cliente extends Pessoa {

    private String endereco;
}
