package com.petcare.sistema_petshop.repository;

import com.petcare.sistema_petshop.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    UserDetails findByLogin(String login);   // metodo que vai usar para verificar o login valido

}
