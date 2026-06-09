package com.petcare.sistema_petshop.Service;

import com.petcare.sistema_petshop.dto.FuncionarioRequestDTO;
import com.petcare.sistema_petshop.model.Funcionario;
import com.petcare.sistema_petshop.repository.FuncionarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FuncionarioService {

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    public List<Funcionario> listarTodos(){
        return funcionarioRepository.findAll();
    }

    public Funcionario salvar(FuncionarioRequestDTO dados){
        Funcionario funcionario = new Funcionario();
        funcionario.setNome(dados.nome());
        funcionario.setTelefone(dados.telefone());
        funcionario.setCpf(dados.cpf());
        funcionario.setCargo(dados.cargo());
        funcionario.setEspecialidade(dados.especialidade());

        return funcionarioRepository.save(funcionario);
    }

    public void deletar(Long id) {
        if (funcionarioRepository.existsById(id)) {
            funcionarioRepository.deleteById(id);
        } else {
            throw new RuntimeException("Funcionário não encontrado com o ID pra deletar ");
        }
    }



}
