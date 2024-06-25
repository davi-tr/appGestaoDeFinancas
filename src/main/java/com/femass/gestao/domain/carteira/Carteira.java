package com.femass.gestao.domain.carteira;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.femass.gestao.domain.entradas.Entrada;
import com.femass.gestao.domain.gasto.Gasto;
import com.femass.gestao.domain.usuario.Usuario;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name= "Carteira")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Carteira {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name = "usuario_id")
    Usuario usuario;
    private BigDecimal valorDisponivel;
    private BigDecimal Saldo;
    @OneToMany(mappedBy = "carteira")
    List<Gasto> gastos;
    @OneToMany(mappedBy = "carteira")
    List<Entrada> entradas;
    private BigDecimal totalEntradas;
    private BigDecimal totalSaidas;

    public void addGasto(Gasto gasto) {
        gastos.add(gasto);
    }

    public void removeGasto(Gasto gasto) {
        gastos.remove(gasto);
    }

    public void addEntrada(Entrada entrada) {
        entradas.add(entrada);
    }

    public void removeEntrada(Entrada entrada) {
        entradas.remove(entrada);
    }

    @JsonIgnoreProperties
    ZoneId zoneId = ZoneId.of("America/Sao_Paulo");

    public void updateValorDisponivel(Integer interval) {
        var total = getValorDisponivel();
        for(Gasto gasto : gastos){
            ZonedDateTime now = ZonedDateTime.now(ZoneId.of("America/Sao_Paulo"));
            ZonedDateTime trintaDias = now.plusDays(-interval);
            if(!gasto.getDataEntrada().toInstant().isBefore(trintaDias.toInstant())){
                if(gasto.getValor().compareTo(BigDecimal.ZERO)>0){
                    gasto.setValor(gasto.getValor().multiply(BigDecimal.valueOf(-1)));
                }
                if(gasto.getEparcela()){
                    var controleParc = gasto.getValorParcela().multiply(BigDecimal.valueOf(-1));
                    this.totalSaidas = totalSaidas.add(controleParc);
                    if(totalSaidas.compareTo(BigDecimal.ZERO)>0){
                        totalSaidas = totalSaidas.multiply(BigDecimal.valueOf(-1));
                    }
                    total = (total.add(controleParc));
                    setValorDisponivel(total);
                }else{
                    this.totalSaidas = totalSaidas.add(gasto.getValor());
                    total = (total.add(gasto.getValor()));
                    setValorDisponivel(total);
                }
            }

        }
        for(Entrada entrada : entradas){
            ZonedDateTime now = ZonedDateTime.now(ZoneId.of("America/Sao_Paulo"));
            ZonedDateTime trintaDias = now.plusDays(-interval);
            if(!entrada.getDataEntrada().toInstant().isBefore(trintaDias.toInstant())){
                total = (total.add(entrada.getValor()));
                setValorDisponivel(total);
            }

        }
    }

    public void updateValorDisponivel() {
        var total = getValorDisponivel();
        for(Gasto gasto : gastos){
            ZonedDateTime now = ZonedDateTime.now(ZoneId.of("America/Sao_Paulo"));
            ZonedDateTime trintaDias = now.plusDays(-30);
            if(!gasto.getDataEntrada().toInstant().isBefore(trintaDias.toInstant())){
                if(!gasto.getDataEntrada().toInstant().isAfter(ZonedDateTime.now().toInstant())) {
                    if (gasto.getValor().compareTo(BigDecimal.ZERO) > 0) {
                        gasto.setValor(gasto.getValor().multiply(BigDecimal.valueOf(-1)));
                    }
                    if (gasto.getEparcela()) {
                        var controleParc = gasto.getValorParcela().multiply(BigDecimal.valueOf(-1));
                        this.totalSaidas = totalSaidas.add(controleParc);
                        if (totalSaidas.compareTo(BigDecimal.ZERO) > 0) {
                            totalSaidas = totalSaidas.multiply(BigDecimal.valueOf(-1));
                        }
                        total = (total.add(controleParc));
                        setValorDisponivel(total);
                    } else {
                        this.totalSaidas = totalSaidas.add(gasto.getValor());
                        total = (total.add(gasto.getValor()));
                        setValorDisponivel(total);
                    }
                }
            }

        }
        for(Entrada entrada : entradas){
            ZonedDateTime now = ZonedDateTime.now(ZoneId.of("America/Sao_Paulo"));
            ZonedDateTime trintaDias = now.plusDays(-30);
            if(!entrada.getDataEntrada().toInstant().isBefore(trintaDias.toInstant())){
                if(!entrada.getDataEntrada().toInstant().isAfter(ZonedDateTime.now().toInstant())) {
                    total = (total.add(entrada.getValor()));
                    setValorDisponivel(total);
                }
            }

        }
    }

    public void updateValorDisponivelFuturo(Integer diasMais) {
        var total = getValorDisponivel();
        for(Gasto gasto : gastos){
            ZonedDateTime now = ZonedDateTime.now(ZoneId.of("America/Sao_Paulo"));
            ZonedDateTime trintaDias = now.plusDays(-30);
            ZonedDateTime avanco = now.plusDays(diasMais);
            if(!gasto.getDataEntrada().toInstant().isBefore(trintaDias.toInstant())){
                if(!gasto.getDataEntrada().toInstant().isAfter(avanco.toInstant())) {
                    if (gasto.getValor().compareTo(BigDecimal.ZERO) > 0) {
                        gasto.setValor(gasto.getValor().multiply(BigDecimal.valueOf(-1)));
                    }
                    if (gasto.getEparcela()) {
                        var controleParc = gasto.getValorParcela().multiply(BigDecimal.valueOf(-1));
                        this.totalSaidas = totalSaidas.add(controleParc);
                        if (totalSaidas.compareTo(BigDecimal.ZERO) > 0) {
                            totalSaidas = totalSaidas.multiply(BigDecimal.valueOf(-1));
                        }
                        total = (total.add(controleParc));
                        setValorDisponivel(total);
                    } else {
                        this.totalSaidas = totalSaidas.add(gasto.getValor());
                        total = (total.add(gasto.getValor()));
                        setValorDisponivel(total);
                    }
                }
            }

        }
        for(Entrada entrada : entradas){
            ZonedDateTime now = ZonedDateTime.now(ZoneId.of("America/Sao_Paulo"));
            ZonedDateTime trintaDias = now.plusDays(-30);
            ZonedDateTime avanco = now.plusDays(diasMais);
            if(!entrada.getDataEntrada().toInstant().isBefore(trintaDias.toInstant())){
                if(!entrada.getDataEntrada().toInstant().isAfter(avanco.toInstant())) {
                    total = (total.add(entrada.getValor()));
                    setValorDisponivel(total);
                }
            }

        }
    }

    public void addUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void getTotalEntradaSaidasGerenciavel(Integer intervalo){
        ZonedDateTime now = ZonedDateTime.now(ZoneId.of("America/Sao_Paulo"));
        ZonedDateTime trintaDias = now.plusDays(-intervalo);
        this.setTotalSaidas(BigDecimal.ZERO);
        this.setTotalEntradas(BigDecimal.ZERO);
        System.out.println("lul");
        for(Entrada entrada : entradas){
            if(!entrada.getDataEntrada().toInstant().isBefore(trintaDias.toInstant())){
                this.totalEntradas = totalEntradas.add(entrada.getValor());
            }
        }

        for(Gasto gasto : gastos){
            if(!gasto.getDataEntrada().toInstant().isBefore(trintaDias.toInstant())){
                if(gasto.getEparcela()){
                    BigDecimal controlaParc = gasto.getValorParcela().multiply(BigDecimal.valueOf(-1));
                    this.totalSaidas = totalSaidas.add(controlaParc);
                    if(gasto.getDataProxParcela().toInstant().isBefore(ZonedDateTime.now().toInstant())){
                        gasto.setParcelaAtual(gasto.getParcelaAtual()+1);
                        gasto.setParcelaRestante(gasto.getParcelas()-gasto.getParcelaAtual());
                        gasto.setDataProxParcela(ZonedDateTime.now(ZoneId.of("America/Sao_Paulo")).plusDays(30));
                    }
                    gasto.setDataUltimaParcela(gasto.getDataProxParcela().plusMonths(gasto.getParcelaRestante()));
                }else{
                    this.totalSaidas = totalSaidas.add(gasto.getValor());
                }
            }
        }
    }

    public void getTotalEntradasSaidas(){
        ZonedDateTime now = ZonedDateTime.now(ZoneId.of("America/Sao_Paulo"));
        ZonedDateTime trintaDias = now.plusDays(-30);
        this.setTotalSaidas(BigDecimal.ZERO);
        this.setTotalEntradas(BigDecimal.ZERO);
        for(Entrada entrada : entradas){
            if(!entrada.getDataEntrada().toInstant().isBefore(trintaDias.toInstant())){
                if(!entrada.getDataEntrada().isAfter(ZonedDateTime.now())) {
                    this.totalEntradas = totalEntradas.add(entrada.getValor());
                }
            }
        }

        for(Gasto gasto : gastos) {
            if (!gasto.getDataEntrada().toInstant().isBefore(trintaDias.toInstant())) {
                if (!gasto.getDataEntrada().toInstant().isAfter(ZonedDateTime.now().toInstant())){
                    if (gasto.getEparcela()) {
                        BigDecimal controlaParc = gasto.getValorParcela().multiply(BigDecimal.valueOf(-1));
                        this.totalSaidas = totalSaidas.add(controlaParc);
                        if (gasto.getDataProxParcela().toInstant().isBefore(ZonedDateTime.now().toInstant())) {
                            gasto.setParcelaAtual(gasto.getParcelaAtual() + 1);
                            gasto.setParcelaRestante(gasto.getParcelas() - gasto.getParcelaAtual());
                            gasto.setDataProxParcela(ZonedDateTime.now(ZoneId.of("America/Sao_Paulo")).plusDays(30));
                        }
                        gasto.setDataUltimaParcela(gasto.getDataProxParcela().plusMonths(gasto.getParcelaRestante()));
                    } else {
                        this.totalSaidas = totalSaidas.add(gasto.getValor());
                    }
                }
        }
        }

    }

    public void getTotalEntradasSaidasFuturo(Integer inter){
        ZonedDateTime now = ZonedDateTime.now(ZoneId.of("America/Sao_Paulo"));
        ZonedDateTime trintaDias = now.plusDays(-30);
        ZonedDateTime avanco = now.plusDays(inter);
        this.setTotalSaidas(BigDecimal.ZERO);
        this.setTotalEntradas(BigDecimal.ZERO);
        for(Entrada entrada : entradas){
            if(entrada.getDataEntrada().toInstant().isAfter(now.toInstant())&& entrada.getDataEntrada().toInstant().isBefore(avanco.toInstant())){
                    this.totalEntradas = totalEntradas.add(entrada.getValor());
            }
        }

        for(Gasto gasto : gastos) {
            if (gasto.getDataEntrada().toInstant().isAfter(now.toInstant()) && gasto.getDataEntrada().toInstant().isBefore(avanco.toInstant())) {
                    if (gasto.getEparcela()) {
                        BigDecimal controlaParc = gasto.getValorParcela().multiply(BigDecimal.valueOf(-1));
                        this.totalSaidas = totalSaidas.add(controlaParc);
                        if (gasto.getDataProxParcela().toInstant().isBefore(ZonedDateTime.now().toInstant())) {
                            gasto.setParcelaAtual(gasto.getParcelaAtual() + 1);
                            gasto.setParcelaRestante(gasto.getParcelas() - gasto.getParcelaAtual());
                            gasto.setDataProxParcela(ZonedDateTime.now(ZoneId.of("America/Sao_Paulo")).plusDays(30));
                        }
                        gasto.setDataUltimaParcela(gasto.getDataProxParcela().plusMonths(gasto.getParcelaRestante()));
                    } else {
                        this.totalSaidas = totalSaidas.add(gasto.getValor());
                    }
            }
        }

    }

    public void getTotalEntradasSaidas(Integer interval){
        ZonedDateTime now = ZonedDateTime.now(ZoneId.of("America/Sao_Paulo"));
        ZonedDateTime trintaDias = now.plusDays(-interval);
        this.setTotalSaidas(BigDecimal.ZERO);
        this.setTotalEntradas(BigDecimal.ZERO);
        for(Entrada entrada : entradas){
            if(!entrada.getDataEntrada().toInstant().isBefore(trintaDias.toInstant())){
                this.totalEntradas = totalEntradas.add(entrada.getValor());
            }
        }

        for(Gasto gasto : gastos){
            if(!gasto.getDataEntrada().toInstant().isBefore(trintaDias.toInstant())){
                this.totalSaidas = totalSaidas.add(gasto.getValor());
            }

        }

    }

    public List<Object> getGastosMesNext(Integer interval) {
        int limite = 1;
        int index = 0;
        List<Object> gastosN = new ArrayList<>();
        for (Gasto gasto : this.gastos) {
            ZonedDateTime now = ZonedDateTime.now();
            ZonedDateTime trintaDias = now.plusDays(-30);
            ZonedDateTime avanco = now.plusDays(interval);
            if(gasto.getDataEntrada().toInstant().isAfter(now.toInstant()) && gasto.getDataEntrada().toInstant().isBefore(avanco.toInstant())) {
                    gastosN.add(gasto);
            }
        }
        for (Entrada entrada : this.entradas) {
            ZonedDateTime now = ZonedDateTime.now(ZoneId.of("America/Sao_Paulo"));
            ZonedDateTime trintaDias = now.plusDays(-30);
            ZonedDateTime avanco = now.plusDays(interval);
            if(entrada.getDataEntrada().toInstant().isAfter(now.toInstant())&& entrada.getDataEntrada().toInstant().isBefore(avanco.toInstant())){
                    gastosN.add(entrada);
            }
        }
        return gastosN;
    }


    public List<Object> getGastosMes() {
        int limite = 1;
        int index = 0;
        List<Object> gastosN = new ArrayList<>();
        for (Gasto gasto : this.gastos) {
                ZonedDateTime now = ZonedDateTime.now();
                ZonedDateTime trintaDias = now.plusDays(-30);
                if(!gasto.getDataEntrada().toInstant().isBefore(trintaDias.toInstant())) {
                    if (!gasto.getDataEntrada().toInstant().isAfter(ZonedDateTime.now().toInstant())) {
                        gastosN.add(gasto);
                    }
                }
        }
        for (Entrada entrada : this.entradas) {
            ZonedDateTime now = ZonedDateTime.now(ZoneId.of("America/Sao_Paulo"));
            ZonedDateTime trintaDias = now.plusDays(-30);
            if(!entrada.getDataEntrada().toInstant().isBefore(trintaDias.toInstant())){
                if(!entrada.getDataEntrada().toInstant().isAfter(now.toInstant())){
                    gastosN.add(entrada);
                }
            }
        }
        return gastosN;
    }

    public List<Object> getGastosInterval(Integer intervalo) {
        int limite = 1;
        int index = 0;
        List<Object> gastosN = new ArrayList<>();
        for (Gasto gasto : this.gastos) {
            ZonedDateTime now = ZonedDateTime.now(ZoneId.of("America/Sao_Paulo"));
            ZonedDateTime trintaDias = now.plusDays(-intervalo);
            if(!gasto.getDataEntrada().toInstant().isBefore(trintaDias.toInstant())) {
                if (!gasto.getDataEntrada().toInstant().isAfter(now.toInstant())) {
                    gastosN.add(gasto);
                }
            }
        }
        for (Entrada entrada : this.entradas) {
            ZonedDateTime now = ZonedDateTime.now(ZoneId.of("America/Sao_Paulo"));
            ZonedDateTime trintaDias = now.plusDays(-intervalo);
            if(!entrada.getDataEntrada().toInstant().isBefore(trintaDias.toInstant())) {
                if (!entrada.getDataEntrada().toInstant().isAfter(now.toInstant())) {
                    gastosN.add(entrada);
                }
            }
        }
        return gastosN;
    }

}
