package com.petcare.sistema_petshop.Service;

import com.petcare.sistema_petshop.dto.ClienteRequestDTO;
import com.petcare.sistema_petshop.model.Cliente;
import com.petcare.sistema_petshop.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service      // anotacao que faz entender que a classe é uma service importante para as regras
public class ClienteService {

    @Autowired    // injencao de dependendia do obejeto para nao precisar do instanciar tradicionalmente
    private ClienteRepository clienteRepository;

    public List<Cliente> listarTodos(){
        return clienteRepository.findAll();
    }

    public Cliente salvar(ClienteRequestDTO dados){
        Cliente cliente = new Cliente();

        //seta os dados que vêm da herança de Pessoa e da classe Cliente
        cliente.setNome(dados.nome());
        cliente.setCpf(dados.cpf());
        cliente.setTelefone(dados.telefone());
        cliente.setEndereco(dados.endereco());

        return clienteRepository.save(cliente);
    }

    public void deletar(Long id) {
        if (!clienteRepository.existsById(id)) {
            throw new RuntimeException("Erro: Cliente não encontrado para exclusão.");
        }
        clienteRepository.deleteById(id);
    }


}
