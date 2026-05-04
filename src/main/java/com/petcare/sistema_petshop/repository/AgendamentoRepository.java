package com.petcare.sistema_petshop.repository;

import com.petcare.sistema_petshop.model.Agendamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository                                                            // anotacao que faz entender que isso é a classe que se comunica com o banco de dados
public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {   // pega os metodos da JpaRepository( Interface ) para nao lotar meu codigo
        // no generics eu passo com quem estou trabalhando e qual tipo de Id da classe que estou trabalhando
}
