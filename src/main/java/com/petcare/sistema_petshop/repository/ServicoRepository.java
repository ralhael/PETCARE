package com.petcare.sistema_petshop.repository;

import com.petcare.sistema_petshop.model.Servico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository                                                 // anotacao que faz entender que é uma repository que é a que se comunica direto com o banco de dados
public interface ServicoRepository extends JpaRepository<Servico, Long> {
}
