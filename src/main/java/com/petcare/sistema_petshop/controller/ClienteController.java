package com.petcare.sistema_petshop.controller;

import com.petcare.sistema_petshop.Service.ClienteService;
import com.petcare.sistema_petshop.model.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController                         // é anotacao que serve para fazer a comunicacao com a web e tranformar a listas automaticamente em Json
@RequestMapping("/clientes")            // é o endereço que vai rodar no localHost
public class ClienteController {

    @Autowired                  // injeta a instancia automaticamente nesse caso da service
    ClienteService clienteService;

    @GetMapping                 // anotacao que espera que pegue/leia dados  ( metodo get )
    public List<Cliente> listar(){
        return clienteService.listarTodos();
    }

    @PostMapping      // anotacao para dizer que espera o envio de dados ( metodo Post )
    public Cliente salvar(@RequestBody Cliente cliente){      // traducao de Json para transformar em um objeto java Cliente
        return clienteService.salvar(cliente);
    }
}
