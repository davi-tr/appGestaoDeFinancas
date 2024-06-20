package com.femass.gestao.domain.gasto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.femass.gestao.domain.carteira.Carteira;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.lang.Nullable;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name= "Gastos")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Gasto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "carteira_id", nullable = false)
    @JsonIgnore
    protected Carteira carteira;

    private BigDecimal valor;
    private boolean eParcela;
    private BigDecimal valorParcela;
    private String descricao;
    private String Local;
    private ZonedDateTime dataEntrada;
    private ZonedDateTime dataProxParcela;
    private int parcelaAtual;
    private int parcelaRestante;
    private int parcelas;
    private ZonedDateTime dataUltimaParcela;
    @Enumerated(EnumType.STRING)
    private Categorias categoria;

    public Gasto(DadosGasto dadosGasto) {
        this.valor = dadosGasto.valor();
        this.descricao = dadosGasto.descricao();
        this.Local = dadosGasto.Local();
        if(dadosGasto.eparcela()){
            this.valorParcela = dadosGasto.valorParcela();
            this.parcelas = dadosGasto.parcelas();
            this.valor = valorParcela.multiply(BigDecimal.valueOf(parcelas));
            this.parcelaAtual = 1;
            this.parcelaRestante = parcelas - parcelaAtual;
            this.dataProxParcela = ZonedDateTime.now(ZoneId.of("America/Sao_Paulo")).plusDays(30);
            this.eParcela = true;
            this.dataUltimaParcela = ZonedDateTime.now(ZoneId.of("America/Sao_Paulo")).plusMonths(parcelas);
        }
        this.dataEntrada = ZonedDateTime.now(ZoneId.of("America/Sao_Paulo"));
        this.categoria = dadosGasto.categoria();
    }

    public boolean getEparcela(){
        return eParcela;
    }
    public void updateGasto(DadosGasto dadosGasto){
        if (dadosGasto.valor() != valor){
            this.valor = dadosGasto.valor();
        }
        if (dadosGasto.descricao() != descricao){
            this.descricao = dadosGasto.descricao();
        }
        if(dadosGasto.Local() != Local){
            this.Local = dadosGasto.Local();
        }
        if(dadosGasto.eparcela()){
            this.valorParcela = dadosGasto.valorParcela();
            this.parcelas = dadosGasto.parcelas();
            this.valor = valorParcela.multiply(BigDecimal.valueOf(parcelas));
            this.eParcela = true;
            if(dadosGasto.parcelas()>parcelas){
                this.dataUltimaParcela = dataEntrada.plusMonths(dadosGasto.parcelas());
            }
        }
    }

    public void removeCard(Carteira carteira) {
        setCarteira(null);
    }
}
