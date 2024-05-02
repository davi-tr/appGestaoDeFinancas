package com.femass.gestao.repository.carteira;

import com.femass.gestao.domain.carteira.Carteira;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CarteiraRepository extends JpaRepository<Carteira, Long> {
    Optional<Carteira> findById(Long id);
}
