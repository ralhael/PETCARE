package com.petcare.sistema_petshop.controller;

import com.petcare.sistema_petshop.Service.AnimalService;
import com.petcare.sistema_petshop.model.Animal;
import com.petcare.sistema_petshop.model.Cao;
import com.petcare.sistema_petshop.model.Gato;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController             // anotacao que faz fazer a comunicao com a web e transformar a lista em Json
@RequestMapping("/animais") // é o endpoint caminho do localHost
public class AnimalController {

    @Autowired                 // faz a injecao de instancia
     AnimalService animalService;

    @GetMapping                 // anotacao que faz enteder o metodo que vai buscar ou ler ( metodo get )
    public List<Animal> listar(){
        return animalService.listarTodos();
    }

    @PostMapping("/cao")               // anotacao que faz entender que é para criacao ( metodo create  )
   public Animal salvarCao(@RequestBody Cao cao){
            return animalService.salvar(cao);
    }

    @PostMapping("/gato")
    public Animal salvarGato(@RequestBody Gato gato){
        return animalService.salvar(gato);
    }

}
