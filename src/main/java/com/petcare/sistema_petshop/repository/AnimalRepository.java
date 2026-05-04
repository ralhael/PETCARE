package com.petcare.sistema_petshop.repository;

import com.petcare.sistema_petshop.model.Animal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository     // serve para dizer que é do tipo repository ( ela é a que fala com o banco de dados )
public interface AnimalRepository extends JpaRepository<Animal, Long> { // herdar os metodos da JpaRepository (Interface) para nao lotar meu codigo
    // (<>serve para dizer com quem eu estou trabalhando(classe Animal e o tipo do ID) )
}
