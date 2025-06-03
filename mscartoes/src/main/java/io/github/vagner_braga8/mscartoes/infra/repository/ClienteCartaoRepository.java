package io.github.vagner_braga8.mscartoes.infra.repository;

import io.github.vagner_braga8.mscartoes.domain.ClienteCartao;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ClienteCartaoRepository extends CrudRepository<ClienteCartao, Long> {
    List<ClienteCartao> findByCpf(String cpf);
}
