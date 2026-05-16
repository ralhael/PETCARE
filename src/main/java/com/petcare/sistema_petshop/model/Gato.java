package com.petcare.sistema_petshop.model;


import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data                                       // anotacao que traz todos os metodos chatos
@EqualsAndHashCode(callSuper = true)        // faz com que pegue os metodos e atributos e compare para saber se sao iguais
@Entity                                     // anotacao que faz entender que é uma entidade ( tabela no banco de dados)
@DiscriminatorValue(value = "gato")         // oq vai aparecer no banco qnd instanciar
@NoArgsConstructor
public class Gato extends Animal {

    private String racaCor;


}
