package com.petcare.sistema_petshop.model;

import jakarta.persistence.*;
import lombok.Data;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data     // por baixo dos panos os metodos chatos (get set equal toString)
@Entity   // indicar ao Hibernate/Jpa que vai ser uma tabela no banco de dados
@Table(name = "usuarios")           // forçar a criar a tabela com esse nome
public class Usuario implements UserDetails {

    @Id         // indicar que é um id chave primaria ao banco
    @GeneratedValue(strategy = GenerationType.IDENTITY)         // que deve ser criado sequencialmente os Ids
    Long id;

    @Column(unique = true, nullable = false)        // no campo login que vai ser Email impede que seja campo unico ( unique = true duas pessoas n tem o mesmo ) e que nao pode vir nulo
    private String login;

    @Column(nullable = false)  // define apenas a trava que nao deve vir nulo
    private String senha;



    // esses metodos vem da interface userDetails
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {        // por enquanto qualquer user que cadastrar tem acesso adm
            return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"));
    }

    @Override
    public @Nullable String getPassword() {  // diz qual pro spring quem é a senha
        return this.senha;
    }

    @Override
    public String getUsername() {           // diz qual pro spring quem é o email
        return this.login;
    }

    @Override
    public boolean isAccountNonExpired() {      // conta nao ta expirada
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {       // conta nao ta bloqueada
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {      // senha nao esta expirada
        return true;
    }

    @Override
    public boolean isEnabled() {            // ativo e habilitado
        return true;
    }
}
