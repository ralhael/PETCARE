package com.petcare.sistema_petshop.Service;

import com.petcare.sistema_petshop.dto.ServicoRequestDTO;
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

    public Servico salvar(ServicoRequestDTO dados){
        Servico servico  = new Servico();
        servico.setDescricao(dados.descricao());
        servico.setPreco(dados.preco());

        return servicoRepository.save(servico);
    }

    public void deletar(Long id){
        if(!servicoRepository.existsById(id)){
            throw new RuntimeException("Erro : Servico nao encontrado para deletar");
        }
        servicoRepository.deleteById(id);
    }

}
