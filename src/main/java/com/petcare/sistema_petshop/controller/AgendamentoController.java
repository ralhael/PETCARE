package com.petcare.sistema_petshop.controller;

import com.petcare.sistema_petshop.Service.AgendamentoService;
import com.petcare.sistema_petshop.dto.AgendamentoRequestDTO;
import com.petcare.sistema_petshop.model.Agendamento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RequestMapping("/agendamentos")        // É o caminho/endpoint que vai servir para rodar no localhost dessa função específica
@RestController                         // Anotação que faz entender que é responsável por se comunicar com a web e fazer Lista -> JSON
public class AgendamentoController {

    @Autowired                          // Faz a injeção de instância da service
    private AgendamentoService agendamentoService;

    @GetMapping                         // Anotação que declara que é um  de ler/pegar (GET)
    public List<Agendamento> listarTodos(){
        return agendamentoService.listarTodos();
    }

    @PostMapping
    public ResponseEntity<Agendamento> salvar(@RequestBody AgendamentoRequestDTO dados){
        // Agora recebe o DTO com os IDs e envia para a service tratar
        Agendamento agendamentoSalvo = agendamentoService.salvar(dados);
        return ResponseEntity.ok(agendamentoSalvo);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Agendamento> atualizarAgendamento(@PathVariable Long id, @RequestBody Agendamento dadosNovos){
        Agendamento agendamentoAtualizado = agendamentoService.atualizarAgendamento(id , dadosNovos);
        return ResponseEntity.ok(agendamentoAtualizado);
    }

    @GetMapping("/cliente/{clienteId}")         // Vai ser uma requisição do tipo GET recebendo o ID do cliente na URL
    public ResponseEntity<List<Agendamento>> buscarPorCliente(@PathVariable Long clienteId){
        List<Agendamento> listaAgendamentos = agendamentoService.buscarPorCliente(clienteId);
        return ResponseEntity.ok(listaAgendamentos);                                                // Retorna o status 200 sucesso com a lista
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable long id){
        agendamentoService.cancelarAgendamento(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/data/{data}")
    public ResponseEntity<List<Agendamento>> bucarPorData(@PathVariable LocalDate data){
        List<Agendamento> lista = agendamentoService.buscarPorData(data);
        return ResponseEntity.ok(lista);
    }
}