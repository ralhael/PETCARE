package com.petcare.sistema_petshop.controller;

import com.petcare.sistema_petshop.Service.ServicoService;
import com.petcare.sistema_petshop.model.Servico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/servicos")
@RestController
public class ServicoController {

    @Autowired
    ServicoService servicoService;

    @GetMapping
    public List<Servico> listarTodos(){
        return servicoService.listarTodos();
    }

    @PostMapping
    public Servico salvar(@RequestBody Servico servico){
        return servicoService.salvar(servico);
    }

}
