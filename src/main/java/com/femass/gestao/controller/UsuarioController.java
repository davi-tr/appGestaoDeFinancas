package com.femass.gestao.controller;

import com.femass.gestao.domain.carteira.Carteira;
import com.femass.gestao.domain.carteira.DadosGastoLimitado;
import com.femass.gestao.domain.gasto.DadosGasto;
import com.femass.gestao.domain.gasto.Gasto;
import com.femass.gestao.domain.usuario.DadosUnicoUsuario;
import com.femass.gestao.domain.usuario.DadosUsuario;
import com.femass.gestao.domain.usuario.Usuario;
import com.femass.gestao.repository.carteira.CarteiraRepository;
import com.femass.gestao.repository.gasto.GastoRepository;
import com.femass.gestao.repository.usuario.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioRepository usuarioRepository;
    private final CarteiraRepository carteiraRepository;
    private final GastoRepository gastoRepository;

    @GetMapping()
    public ResponseEntity getuser(@RequestBody DadosUsuario dadosUsuario){
        Usuario usuario = this.usuarioRepository.getReferenceById(dadosUsuario.id());
        return ResponseEntity.ok(new DadosUnicoUsuario(usuario.getNome(), usuario.getEmail(), usuario.getNumeroTelefone(), usuario.getLogin(), usuario.getCarteira().getId(), usuario.getCarteira().getSalario()));
    }

    @PutMapping()
    public ResponseEntity updateuser(@RequestBody DadosUsuario dadosUsuario){
        Usuario usuario = this.usuarioRepository.getReferenceById(dadosUsuario.id());
        usuario.getCarteira().setSalario(dadosUsuario.Saldo());
        return ResponseEntity.ok(new DadosUnicoUsuario(usuario.getNome(), usuario.getEmail(), usuario.getNumeroTelefone(), usuario.getLogin(), usuario.getCarteira().getId(), usuario.getCarteira().getSalario()));
    }

    @PutMapping("/gasto")
    public ResponseEntity createGasto(@RequestBody DadosGasto dadosGasto){
        Carteira carteira = this.carteiraRepository.getReferenceById(dadosGasto.idCarteira());
        Gasto gasto = new Gasto(dadosGasto);
        gasto.setCarteira(carteira);
        carteira.addGasto(gasto);
        carteiraRepository.save(carteira);
        gastoRepository.save(gasto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/carteira")
    public ResponseEntity getCarteira(@RequestBody DadosUsuario dadosUsuario){
        Usuario usuario = this.usuarioRepository.getReferenceById(dadosUsuario.id());
        Carteira carteira = this.carteiraRepository.getReferenceById(usuario.getCarteira().getId());
        DadosGastoLimitado DadosGastoLimitado = new DadosGastoLimitado(carteira.getId(), carteira.getGastos20());
        System.out.println(DadosGastoLimitado);
        return ResponseEntity.ok(new DadosGastoLimitado(carteira.getId(), carteira.getGastos20()));

    }

}
