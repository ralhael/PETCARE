package com.petcare.sistema_petshop.Service;

import com.petcare.sistema_petshop.model.Servico;
import com.petcare.sistema_petshop.repository.ServicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicoService {

    @Autowired
    private ServicoRepository servicoRepository;

    public List<Servico> listarTodos(){
        return servicoRepository.findAll();
    }

    public Servico salvar(Servico servico){
        return servicoRepository.save(servico);
    }

}
