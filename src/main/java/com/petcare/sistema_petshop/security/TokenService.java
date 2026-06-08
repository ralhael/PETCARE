package com.petcare.sistema_petshop.security;

import com.petcare.sistema_petshop.model.Usuario;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Service
public class TokenService {

    //   string longa e fixa para que o token não mude ou quebre se o servidor reiniciar
    private final String ASSINATURA_TEXTO = "minha-chave-secreta-super-ultra-segura-do-petshop-2026";
    private final Key CHAVE_SECRETA = Keys.hmacShaKeyFor(ASSINATURA_TEXTO.getBytes(StandardCharsets.UTF_8));

    private final Long TEMPO_EXPIRACAO = 7200000L; // quanto tempo dura o token (2 horas)

    public String gerarToken(Usuario usuario){
        Date agora = new Date();
        Date dataExpiracao = new Date(agora.getTime() + TEMPO_EXPIRACAO);

        return Jwts.builder()
                .setIssuer("API Sistema Petshop")  // diz que servidor gerou esse token
                .setSubject(usuario.getLogin())    // guardar o email para saber quem fez a requisicao
                .setIssuedAt(agora)                // data e hora que foi criado
                .setExpiration(dataExpiracao)      // data e hora que ele vai expirar
                .signWith(CHAVE_SECRETA, SignatureAlgorithm.HS256) // força o algoritmo HS256 explicitamente aqui
                .compact();
    }

    public String validarToken(String token){
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(CHAVE_SECRETA)   // quebra a fechadura do token
                    .build()
                    .parseClaimsJws(token)          // tenta ler o conteudo do token
                    .getBody()
                    .getSubject(); // se der certo ele extrai quem ta la dentro que é o email/ login que veio
        }
        catch (JwtException e){ // se tiver diferente , ou expirado retorna null
            return null;
        }
    }
}