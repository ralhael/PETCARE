package com.petcare.sistema_petshop.Service;

import com.petcare.sistema_petshop.model.Animal;
import com.petcare.sistema_petshop.repository.AnimalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service   // anotacao que serve para identificar que é service
public class AnimalService {

    @Autowired              // anotacao que serve para eu n precisar instanciar tradicionalmente
    private AnimalRepository animalRepository;

    public List<Animal> listarTodos(){
        return animalRepository.findAll();
    }

    public Animal salvar (Animal animal){
        return animalRepository.save(animal);
    }


    //metodos futuros para travar possiveis ocasioes


}
