package com.petcare.sistema_petshop.security;

import com.petcare.sistema_petshop.repository.UsuarioRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AutenticacaoService implements UserDetailsService {

    private UsuarioRepository usuarioRepository;



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails usuario = usuarioRepository.findByLogin(username);      // grava em usuario oq a repository acessou no banco oq recebeu de parametro nesse metodo(username)
        if (usuario == null){
            throw new UsernameNotFoundException("Usuario nao encontrado");      // se n achar nada lança esse erro
        }
        return usuario;                                                 // se achar retorna o usuario que achou
    }
}
