package com.petcare.sistema_petshop.model;


import jakarta.persistence.*;
import lombok.Data;

//@alguma coisa é uma anotacao para dizer que faz alguma acao


@Data  // ela ja vem automaticamente todos os metodos chatos ( gett sett , toString )
@Entity // serve para dizer ao Hibernate que ela é uma entidade , assim deve ser uma Tabela no dataBase
@Inheritance(strategy = InheritanceType.JOINED) // estrategia de herança no banco de dados como animal -> cao / gato deve ser criado la

public abstract class Animal {

    @Id              // anotacao que faz entender que é um id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // ele fala pro PostgreSQL para criar esse id sozin qnd cadastrar um novo pet de forma segura com seu mecanismo Sereal
    private Long id;

    private String nome;
    private Integer idade;
    private Double peso;
    private String sexo;
    private String alergias;
    private String temperamento;


}
