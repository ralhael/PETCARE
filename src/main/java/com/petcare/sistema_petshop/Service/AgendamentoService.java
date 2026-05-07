package com.petcare.sistema_petshop.Service;


import com.petcare.sistema_petshop.model.Agendamento;
import com.petcare.sistema_petshop.repository.AgendamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgendamentoService {

    @Autowired
    private AgendamentoRepository agendamentoRepository;

    public List<Agendamento> listarTodos(){
        return agendamentoRepository.findAll();
    }

    public Agendamento salvar(Agendamento agendamento){
        return agendamentoRepository.save(agendamento);
    }

}
