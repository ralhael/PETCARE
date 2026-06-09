package com.petcare.sistema_petshop.controller;

import com.petcare.sistema_petshop.Service.FuncionarioService;
import com.petcare.sistema_petshop.dto.FuncionarioRequestDTO;
import com.petcare.sistema_petshop.model.Funcionario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController                             // anotacao que faz entender que é uma classe q se comunica com a web tranforma as listas em Json
@RequestMapping("/funcionarios")            // é o endpoint que vai fazer rodar no local host
public class FuncionarioController {

    @Autowired                              // injecao de instancia que faz nao precisar instanciar o objeto
    FuncionarioService funcionarioService;

    @GetMapping                             // anotacao que faz entender que é um metodo para pegar/ler (metodo get)
    public List<Funcionario> listarTodos(){
        return funcionarioService.listarTodos();
    }

    @PostMapping
    public ResponseEntity<Funcionario> salvar(@RequestBody FuncionarioRequestDTO funcionarioDTO) {
        Funcionario funcionarioSalvo = funcionarioService.salvar(funcionarioDTO);
        return ResponseEntity.ok(funcionarioSalvo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        funcionarioService.deletar(id);
        return ResponseEntity.noContent().build();
    }



}
