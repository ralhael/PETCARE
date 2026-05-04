package com.petcare.sistema_petshop.repository;

import com.petcare.sistema_petshop.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository                                         // é a anotacao que faz entender que essa classe é que se comunica com o banco de dados
public interface ClienteRepository extends JpaRepository<Cliente, Long> {       // pega todos os metodos da JpaRepository ( Interface ) para nao lotar o codigo
    // e passa no gererics com que classe estou trabalhando e o tipo de Id dessa classe
}
