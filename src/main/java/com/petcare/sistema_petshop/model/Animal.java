package com.petcare.sistema_petshop.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

//@alguma coisa é uma anotacao para dizer que faz alguma acao

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,   // deixo claro ao jackson que vai levar o apelido da classe nao o nome feio inteiro
        include = JsonTypeInfo.As.PROPERTY,  // dizendo ao jackson como eu vou colocar no Json de forma como outra coluna igual os outras colunas da classe
        property = "type"                   // to dizendo qual vai ser o nome do campo no Json
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = Cao.class, name = "cao"), // mapeamento que o jackson vai procurar na estrutura json o nome exato cao e instanciar a classe cao do java
        @JsonSubTypes.Type(value = Gato.class, name = "gato") // mesmo mapeamento que vai dizer qual classe instanciar qnd encontrar a exata palavra "gato"
})



@Data  // ela ja vem automaticamente todos os metodos chatos ( gett sett , toString )
@Entity // serve para dizer ao Hibernate que ela é uma entidade , assim deve ser uma Tabela no dataBase
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)  // estrategia de herança do banco de dados que deve criar uma coluna para seus filhos e nao uma tabela
@DiscriminatorColumn(name = "tipo_animal", discriminatorType = DiscriminatorType.STRING) // crio qual vai ser o nome da coluna no banco de dados e qual o tipo que vai armazenar
@NoArgsConstructor
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

    @ManyToOne
    @JsonIgnore // evitar tentar puxar cliente dnv aqui do animal
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;


}
