package io.gitgub.vagner_braga8.msavaliadorcredito.application;

import io.gitgub.vagner_braga8.msavaliadorcredito.domain.model.CartaoCliente;
import io.gitgub.vagner_braga8.msavaliadorcredito.domain.model.DadosCliente;
import io.gitgub.vagner_braga8.msavaliadorcredito.domain.model.SituacaoCliente;
import io.gitgub.vagner_braga8.msavaliadorcredito.infra.clients.CartoesResourceClient;
import io.gitgub.vagner_braga8.msavaliadorcredito.infra.clients.ClienteResourceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AvaliadorCreditoService {

    private final ClienteResourceClient clienteResourceClient;
    private final CartoesResourceClient cartoesResourceClient;

    public SituacaoCliente obterSituacaoCliente(String cpf) {
        ResponseEntity<DadosCliente> dadosClienteResponse = clienteResourceClient.dadosDoCliente(cpf);
        ResponseEntity<List<CartaoCliente>> cartoesResponse = cartoesResourceClient.getCartoesByCpf(cpf);

        return SituacaoCliente
                .builder()
                .cliente(dadosClienteResponse.getBody())
                .cartoes(cartoesResponse.getBody())
                .build();
    }

}
