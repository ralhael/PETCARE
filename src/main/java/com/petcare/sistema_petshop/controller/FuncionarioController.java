package com.petcare.sistema_petshop.controller;

import com.petcare.sistema_petshop.Service.FuncionarioService;
import com.petcare.sistema_petshop.model.Funcionario;
import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping                            // anotacao que faz entender que é um metodo que vai criar/colocar ( meotodo post )
    public Funcionario salvar(@RequestBody Funcionario funcionario){            // faz com que ele faça a traducao do Json para a criaçao de um objeto em java
        return funcionarioService.salvar(funcionario);
    }

}
