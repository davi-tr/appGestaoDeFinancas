package com.femass.gestao.controller;

import com.femass.gestao.domain.carteira.Carteira;
import com.femass.gestao.domain.carteira.DadosGastoLimitado;
import com.femass.gestao.domain.entradas.DadosEntrada;
import com.femass.gestao.domain.entradas.Entrada;
import com.femass.gestao.domain.gasto.DadosDelete;
import com.femass.gestao.domain.gasto.DadosGasto;
import com.femass.gestao.domain.gasto.Gasto;
import com.femass.gestao.domain.usuario.DadosUnicoUsuario;
import com.femass.gestao.domain.usuario.DadosUsuario;
import com.femass.gestao.domain.usuario.Usuario;
import com.femass.gestao.repository.carteira.CarteiraRepository;
import com.femass.gestao.repository.entrada.EntradaRepository;
import com.femass.gestao.repository.gasto.GastoRepository;
import com.femass.gestao.repository.usuario.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
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
    private final EntradaRepository entradaRepository;

    @GetMapping()
    public ResponseEntity getuser(@RequestBody DadosUsuario dadosUsuario){
        Usuario usuario = this.usuarioRepository.getReferenceById(dadosUsuario.id());
        return ResponseEntity.ok(new DadosUnicoUsuario(usuario.getNome(), usuario.getEmail(), usuario.getNumeroTelefone(), usuario.getLogin(), usuario.getCarteira().getId(), usuario.getCarteira().getSalario()));
    }

    @PutMapping()
    public ResponseEntity updateuser(@RequestBody DadosUsuario dadosUsuario){
        Usuario usuario = this.usuarioRepository.getReferenceById(dadosUsuario.id());
        usuario.getCarteira().setSalario(dadosUsuario.Saldo());
        usuarioRepository.save(usuario);
        return ResponseEntity.ok(new DadosUnicoUsuario(usuario.getNome(), usuario.getEmail(), usuario.getNumeroTelefone(), usuario.getLogin(), usuario.getCarteira().getId(), usuario.getCarteira().getSalario()));
    }

    @PostMapping("/gasto")
    public ResponseEntity createGasto(@RequestBody DadosGasto dadosGasto){
        Carteira carteira = this.carteiraRepository.getReferenceById(dadosGasto.idCarteira());
        Gasto gasto = new Gasto(dadosGasto);
        gasto.setCarteira(carteira);
        carteira.addGasto(gasto);
        carteira.updateValorDisponivel();
        carteiraRepository.save(carteira);
        gastoRepository.save(gasto);
        return ResponseEntity.ok().build();
    }
    @PutMapping("/gasto")
    public ResponseEntity updateGasto(@RequestBody DadosGasto dadosGasto){
        var carteira = this.carteiraRepository.getReferenceById(dadosGasto.idCarteira());
        var gasto = this.gastoRepository.getReferenceById(dadosGasto.idGasto());
        gasto.updateGasto(dadosGasto);
        carteira.addGasto(gasto);
        carteira.updateValorDisponivel();
        carteiraRepository.save(carteira);
        gastoRepository.save(gasto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/gasto")
    public ResponseEntity deleteGasto(@RequestBody DadosDelete dadosDelete){
        Carteira carteira = this.carteiraRepository.getReferenceById(dadosDelete.idCarteira());
        Gasto gasto = this.gastoRepository.getReferenceById(dadosDelete.id());
        carteira.removeGasto(gasto);
        carteira.updateValorDisponivel();
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/carteira")
    public ResponseEntity getCarteira(@RequestBody DadosUsuario dadosUsuario){
        Usuario usuario = this.usuarioRepository.getReferenceById(dadosUsuario.id());
        Carteira carteira = this.carteiraRepository.getReferenceById(usuario.getCarteira().getId());
        carteira.setValorDisponivel(carteira.getSalario());
        carteira.updateValorDisponivel();
        DadosGastoLimitado DadosGastoLimitado = new DadosGastoLimitado(carteira.getValorDisponivel(),carteira.getId(), carteira.getGastos20());
        System.out.println(DadosGastoLimitado);
        carteiraRepository.save(carteira);
        return ResponseEntity.ok(new DadosGastoLimitado(carteira.getValorDisponivel(),carteira.getId(), carteira.getGastos20()));

    }

    @PostMapping("/entrada")
    public ResponseEntity createEntrada(@RequestBody DadosEntrada dadosEntrada){
        Carteira carteira = this.carteiraRepository.getReferenceById(dadosEntrada.idCarteira());
        Entrada entrada = new Entrada(dadosEntrada);
        entrada.setCarteira(carteira);
        carteira.addEntrada(entrada);
        carteira.updateValorDisponivel();
        carteiraRepository.save(carteira);
        entradaRepository.save(entrada);
        return ResponseEntity.ok().build();
    }
    @PutMapping("/entrada")
    public ResponseEntity updateEntrada(@RequestBody DadosEntrada dadosEntrada){
        var carteira = this.carteiraRepository.getReferenceById(dadosEntrada.idCarteira());
        var entrada = this.entradaRepository.getReferenceById(dadosEntrada.idEntrada());
        entrada.updateEntrada(dadosEntrada);
        carteira.addEntrada(entrada);
        carteira.updateValorDisponivel();
        carteiraRepository.save(carteira);
        entradaRepository.save(entrada);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/entrada")
    public ResponseEntity deleteEntrada(@RequestBody DadosDelete dadosDelete){
        Carteira carteira = this.carteiraRepository.getReferenceById(dadosDelete.idCarteira());
        Entrada entrada = this.entradaRepository.getReferenceById(dadosDelete.id());
        carteira.removeEntrada(entrada);
        carteira.updateValorDisponivel();
        return ResponseEntity.noContent().build();
    }

}
