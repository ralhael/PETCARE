package com.petcare.sistema_petshop.Service;

import com.petcare.sistema_petshop.model.Animal;
import com.petcare.sistema_petshop.model.Cliente;
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

    public Animal salvar(Animal animal, Long clienteId) {
        if (clienteId == null) {
            throw new RuntimeException("Erro: Todo animal precisa de um dono com ID válido!");
        }

        // Busca o cliente real no banco de dados para vincular ao animal
        Cliente clienteBanco = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new RuntimeException("Erro: Cliente dono do pet não encontrado!"));

        animal.setCliente(clienteBanco);
        return animalRepository.save(animal);
    }

    public void deletar(Long id){
        if(!animalRepository.existsById(id))
            throw new RuntimeException("Animal nao encontrado ");
        animalRepository.deleteById(id);
    }






}
