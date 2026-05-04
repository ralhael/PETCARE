package com.petcare.sistema_petshop.model;


import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data                                   // anotacao que pega os metodos chatos ( set get ... )
@EqualsAndHashCode(callSuper = true)    // faz com que pegue os metodos e atributos e compare para saber se sao iguais
@Entity                                 // anotacao que faz ele ser uma entidade ( tabela no database )
public class Funcionario extends Pessoa{

    private String cargo;
    private String especialidade;

}
