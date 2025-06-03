package io.github.vagner_braga8.mscartoes.application;

import io.github.vagner_braga8.mscartoes.domain.Cartao;
import io.github.vagner_braga8.mscartoes.infra.repository.CartaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartaoService {

    private final CartaoRepository cartaoRepository;

    @Transactional
    public Cartao save(Cartao cartao){
        return cartaoRepository.save(cartao);
    }

    public List<Cartao> getCartoesRendaMenorIgual(Long renda){
        BigDecimal rendaMaxima = BigDecimal.valueOf(renda);
        return cartaoRepository.findByRendaLessThanEqual(rendaMaxima);
    }

}
