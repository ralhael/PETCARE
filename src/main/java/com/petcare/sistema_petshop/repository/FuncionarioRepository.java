package com.petcare.sistema_petshop.repository;


import com.petcare.sistema_petshop.model.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository                                         // faz entender que é uma classe repository que conversa direto com o banco de dados
public interface FuncionarioRepository extends JpaRepository<Funcionario , Long> {          // pega todos os metodos da JpaRepository ( Interface ) para manter o codigo mais limpo e nao lotar aqui
    // o gererics eu tenho que passar com quem eu estou trabalhando e o tipo de Id da classe que estou trabalhando
}
