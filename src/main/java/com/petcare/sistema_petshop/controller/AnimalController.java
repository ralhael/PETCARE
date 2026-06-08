package com.petcare.sistema_petshop.controller;

import com.petcare.sistema_petshop.Service.AnimalService;
import com.petcare.sistema_petshop.dto.AnimalRequestDTO;
import com.petcare.sistema_petshop.model.Animal;
import com.petcare.sistema_petshop.model.Cao;
import com.petcare.sistema_petshop.model.Gato;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    public ResponseEntity<Animal> salvarCao(@RequestBody AnimalRequestDTO dados) {
        Cao cao = new Cao();
        // Mapeia os dados gerais
        cao.setNome(dados.nome());
        cao.setIdade(dados.idade());
        cao.setPeso(dados.peso());
        cao.setSexo(dados.sexo());
        cao.setAlergias(dados.alergias());
        cao.setTemperamento(dados.temperamento());

        // Se a sua classe Cao tiver métodos específicos (ex: setRaca ou setPorte), descomente abaixo:
        // cao.setRaca(dados.raca());

        Animal animalSalvo = animalService.salvar(cao, dados.clienteId());
        return ResponseEntity.ok(animalSalvo);
    }

    @PostMapping("/gato")
    public ResponseEntity<Animal> salvarGato(@RequestBody AnimalRequestDTO dados) {
        Gato gato = new Gato();
        gato.setNome(dados.nome());
        gato.setIdade(dados.idade());
        gato.setPeso(dados.peso());
        gato.setSexo(dados.sexo());
        gato.setAlergias(dados.alergias());
        gato.setTemperamento(dados.temperamento());

        // Se a sua classe Gato tiver métodos específicos, descomente abaixo:
        // gato.setRaca(dados.raca());

        Animal animalSalvo = animalService.salvar(gato, dados.clienteId());
        return ResponseEntity.ok(animalSalvo);
    }

        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deletar(@PathVariable Long id){
        animalService.deletar(id);
        return ResponseEntity.noContent().build();
        }

}
