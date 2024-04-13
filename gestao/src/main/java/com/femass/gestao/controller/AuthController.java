package com.femass.gestao.controller;

import com.femass.gestao.domain.DadosCadastroUsuario;
import com.femass.gestao.domain.DadosLoginRepostaUsuario;
import com.femass.gestao.domain.DadosLoginUsuario;
import com.femass.gestao.domain.Usuario;
import com.femass.gestao.infra.security.TokenService;
import com.femass.gestao.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody DadosLoginUsuario dados){
        Usuario usuario = this.usuarioRepository.findByLogin(dados.login()).orElseThrow(()-> new RuntimeException("Usuario não encontrado"));
        if(passwordEncoder.matches(dados.password(), usuario.getPassword())){
            String token = this.tokenService.generateToken(usuario);
            return ResponseEntity.ok(new DadosLoginRepostaUsuario(usuario.getLogin(), token));
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody DadosCadastroUsuario dados){
        Optional<Usuario> usuario = this.usuarioRepository.findByLogin(dados.login());

        if(usuario.isEmpty()){
            Usuario novoUsuario = new Usuario(dados);
            novoUsuario.setPassword(passwordEncoder.encode(dados.password()));
            this.usuarioRepository.save(novoUsuario);
            String token = this.tokenService.generateToken(novoUsuario);
            return ResponseEntity.ok(new DadosLoginRepostaUsuario(novoUsuario.getLogin(), token));
        }

        return ResponseEntity.badRequest().build();
    }
}
