package com.petcare.sistema_petshop.model;


import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data                                  // anotacao para puxar os metodos chatos ( gett sett , toString )
@EqualsAndHashCode(callSuper = true)   // faz com que pegue os metodos e atributos e compare para saber se sao iguais
@Entity                                 // anotacao que serve para dizer que vai ser uma entidade (tabela la no banco)
@DiscriminatorValue(value = "cao")   // oq vai escrever la na coluna do banco de dados qnd instanciar
@NoArgsConstructor
public class Cao extends Animal {


    private String porte;

}
