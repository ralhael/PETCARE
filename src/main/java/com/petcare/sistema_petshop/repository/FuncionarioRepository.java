package com.petcare.sistema_petshop.repository;


import com.petcare.sistema_petshop.model.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository                                         // faz entender que é uma classe repository que conversa direto com o banco de dados
public interface FuncionarioRepository extends JpaRepository<Funcionario , Long> {          // pega todos os metodos da JpaRepository ( Interface ) para manter o codigo mais limpo e nao lotar aqui
    // o gererics eu tenho que passar com quem eu estou trabalhando e o tipo de Id da classe que estou trabalhando


    // query para fazer a busca de todos os funcionarios que nao estao na tabela de agendamento naquele momento (dia e hora)
    @Query("SELECT f FROM Funcionario f WHERE f.id NOT IN " +
            "(SELECT a.funcionario.id FROM Agendamento a WHERE a.data = :data AND a.horario = :horario AND a.funcionario IS NOT NULL)")
    List<Funcionario> findFuncionariosLivres(@Param("data") LocalDate data, @Param("horario") LocalTime horario);
}
