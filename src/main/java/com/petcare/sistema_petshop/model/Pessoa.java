package com.petcare.sistema_petshop.model;


import jakarta.persistence.*;
import lombok.Data;

@Data                               // anotacao que serve para poupar de escrever os metodos chatos ( gett sett toString etc )
@Entity                             // anotacao que faz entender que essa classe é uma entidade ( tabela no dataBase )
@Inheritance(strategy = InheritanceType.JOINED) // anotacao que faz entender que deve ser criado com suas especificaçoes na herança
public abstract class Pessoa  {

    @Id               //anotacao que faz entender que é um id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // anotacao que faz o postgre entender que deve criar automatico e seguramente
    private Long id;
}
