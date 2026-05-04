package com.petcare.sistema_petshop.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data                               // importa todas os metodos chatos de se fazer ( get , set , etc )
@Entity                             // anotacao que faz entender que isso deve ser uma tabela no banco de dados
public class Agendamento {

    @Id                               // anotacao que faz entender que isso é um id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // faz  o postgre entender que o id deve ser criado automaticamente com seu metodo sereal que é mais seguro
    private Long id;


    private LocalDate data;
    private LocalTime horario;
    private String status;


    @ManyToOne                      // varios agendamentos pode pertencer a um Cliente, isso faz o trabalho de nao ter a repeticao toda vida que ele marcar um agendamento
    @JoinColumn(name = "cliente_id") // ela faz o papel da FK , que faz referencia  ao id do cliente ( em conjunto com o ManyToOne )
    private Cliente cliente;


    @ManyToOne                      // Varios agendamentos pode pertencer a um animal ( nao ter a repeticao de every agendamento pro animal criar um novo)
    @JoinColumn(name = "animal_id") // faz o papel de FK para fazer ligacao com id do animal ( em conjunto com o ManyToOne )
    private Animal animal;


    @ManyToOne                      // varios agendamentos pode pertencer a um animal ( para que nao haja repeticao e ele entenda que o agendamento é para aql que ja existe ou se nao existe)
    @JoinColumn(name = "servico_id") // faz o papel de fk para fazer a ligacao com servico ( em conjunto com o ManyToOne )
    private Servico servico;


}
