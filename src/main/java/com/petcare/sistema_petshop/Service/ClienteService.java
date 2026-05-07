package com.petcare.sistema_petshop.Service;

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

    public Cliente salvar(Cliente cliente){
        return clienteRepository.save(cliente);
    }


}
