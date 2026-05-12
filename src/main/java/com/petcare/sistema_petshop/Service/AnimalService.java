package com.petcare.sistema_petshop.Service;

import com.petcare.sistema_petshop.model.Animal;
import com.petcare.sistema_petshop.repository.AnimalRepository;
import com.petcare.sistema_petshop.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service   // anotacao que serve para identificar que é service
public class AnimalService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired              // anotacao que serve para eu n precisar instanciar tradicionalmente
    private AnimalRepository animalRepository;

    public List<Animal> listarTodos(){
        return animalRepository.findAll();
    }

    public Animal salvar (Animal animal){
        if(animal.getCliente()==null || animal.getCliente().getId()==null){
            throw  new RuntimeException("Todo animal precisa de um dono com Id válido!");
        }
        if(!clienteRepository.existsById(animal.getCliente().getId())){
            throw new RuntimeException("Cliente nao encontrado no banco de dados!");
        }
         return animalRepository.save(animal);
    }





}
