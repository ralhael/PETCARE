package com.petcare.sistema_petshop.repository;

import com.petcare.sistema_petshop.model.Agendamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository                                                            // anotacao que faz entender que isso é a classe que se comunica com o banco de dados
public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {   // pega os metodos da JpaRepository( Interface ) para nao lotar meu codigo
        // no generics eu passo com quem estou trabalhando e qual tipo de Id da classe que estou trabalhando
    boolean existsByAnimalIdAndDataAndHorario(Long animalId, LocalDate data, LocalTime horario);

    List<Agendamento> findByClienteId(Long clienteId);  // vai retornar uma lista de agendamentos daquele cliente em especifico passando o Id

    List<Agendamento> findByData(LocalDate data);       // metodo q vai chamar a lista de datas
}
