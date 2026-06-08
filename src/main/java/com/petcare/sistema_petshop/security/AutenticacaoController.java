package com.petcare.sistema_petshop.security;

import com.petcare.sistema_petshop.dto.AutenticacaoDTO;
import com.petcare.sistema_petshop.dto.LoginReponseDTO;
import com.petcare.sistema_petshop.model.Usuario;
import com.petcare.sistema_petshop.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/auth") // Define que todas as rotas dessa classe começam com /auth
public class AutenticacaoController {

    @Autowired
    private AuthenticationManager authenticationManager; // O motor do Spring que valida login/senha

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TokenService tokenService;

    // 🚪 ROTA 1: LOGIN (POST http://localhost:8080/auth/login)
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AutenticacaoDTO dados) {
        // 1. Empacota o login e a senha digitados no formato que o Spring Security exige
        UsernamePasswordAuthenticationToken dadosLogin =
                new UsernamePasswordAuthenticationToken(dados.login(), dados.senha());

        // 2. O AuthenticationManager chama a nossa AutenticacaoService para buscar o usuário e checar a senha criptografada
        Authentication auth = this.authenticationManager.authenticate(dadosLogin);

        // 3. Se a senha estiver certa, gera o Token JWT para esse usuário
        String token = tokenService.gerarToken((Usuario) auth.getPrincipal());

        // 4. Devolve o token dentro do nosso DTO de resposta
        return ResponseEntity.ok(new LoginReponseDTO(token));
    }

    // 📝 ROTA 2: CADASTRO (POST http://localhost:8080/auth/cadastro)
    @PostMapping("/cadastro")
    public ResponseEntity<?> cadastrar(@RequestBody AutenticacaoDTO dados) {
        // 1. Valida se o e-mail/login já não está cadastrado no banco
        if (this.usuarioRepository.findByLogin(dados.login()) != null) {
            return ResponseEntity.badRequest().body("Erro: Este usuário já existe!");
        }

        // 2. Criptografa a senha antes de salvar no banco de dados (BCrypt)
        String senhaCriptografada = new BCryptPasswordEncoder().encode(dados.senha());

        // 3. Cria o novo objeto Usuario com a senha já protegida
        Usuario novoUsuario = new Usuario();
        novoUsuario.setLogin(dados.login());
        novoUsuario.setSenha(senhaCriptografada);

        // 4. Salva no Supabase
        this.usuarioRepository.save(novoUsuario);

        return ResponseEntity.ok().build(); // Retorna Status 200 Sucesso
    }
}
