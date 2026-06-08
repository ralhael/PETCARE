package com.petcare.sistema_petshop.controller;

import com.petcare.sistema_petshop.Service.ServicoService;
import com.petcare.sistema_petshop.dto.ServicoRequestDTO;
import com.petcare.sistema_petshop.model.Servico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Servico> salvar(@RequestBody ServicoRequestDTO dados){
        Servico servicoSalvo = servicoService.salvar(dados);
        return ResponseEntity.ok(servicoSalvo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id){
        servicoService.deletar(id);
        return ResponseEntity.noContent().build();
    }



}
