package com.petcare.sistema_petshop.controller;

import com.petcare.sistema_petshop.Service.AgendamentoService;
import com.petcare.sistema_petshop.model.Agendamento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/agendamentos")        // é o caminho/endpoint que vai servir para rodar no localHost dessa funcao expecifica
@RestController                         // anotacao que faz entender que é responsvael por se comunicar com a web e fzer Lista>Json
public class AgendamentoController{

    @Autowired                          // faz a injencao de instancia
    AgendamentoService agendamentoService;

    @GetMapping                         // anotacao que declara que é um metodo de ler/pegar ( metodo get )
    public List<Agendamento> listarTodos(){
        return agendamentoService.listarTodos();
    }

    @PostMapping                        // anotacao que faz entender que é um metodo de criar/salvar ( metodo post )
    public Agendamento salvar(@RequestBody Agendamento agendamento){
        return agendamentoService.salvar(agendamento);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Agendamento> atualizarAgendamento(@PathVariable Long id, @RequestBody Agendamento dadosNovos){
        Agendamento agendamentoAtualizado = agendamentoService.atualizarAgendamento(id , dadosNovos);
        return ResponseEntity.ok(agendamentoAtualizado);
    }

    @GetMapping("/cliente/{clienteId}")         // vai ser uma requisicao do tipo get e os endpoint que vai ter que usar para poder acessar esse metodo
    public ResponseEntity<List<Agendamento>> buscarPorCliente(@PathVariable Long clienteId){        // response vai controlar a resposta que nos volta ( Path vai pegar a variavel na url e jopga dentro do clientId
        List<Agendamento> listaAgendamentos = agendamentoService.buscarPorCliente(clienteId);       // cria uma lista e guarda oq voltou da Service -> repository -> supa
        return ResponseEntity.ok(listaAgendamentos);                                                // retornar dizendo que foi um sucesso ( codigo 200 )
    }


}
