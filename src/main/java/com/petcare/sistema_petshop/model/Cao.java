package com.petcare.sistema_petshop.model;


import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data                                  // anotacao para puxar os metodos chatos ( gett sett , toString )
@EqualsAndHashCode(callSuper = true)   // faz com que pegue os metodos e atributos e compare para saber se sao iguais
@Entity                                 // anotacao que serve para dizer que vai ser uma entidade (tabela la no banco)
public class Cao extends Animal {


    private String porte;

}
