package io.gitgub.vagner_braga8.msavaliadorcredito.application;

import io.gitgub.vagner_braga8.msavaliadorcredito.domain.model.DadosCliente;
import io.gitgub.vagner_braga8.msavaliadorcredito.domain.model.SituacaoCliente;
import io.gitgub.vagner_braga8.msavaliadorcredito.infra.clients.ClienteResourceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AvaliadorCreditoService {

    private final ClienteResourceClient clienteResourceClient;

    public SituacaoCliente obterSituacaoCliente(String cpf) {
        ResponseEntity<DadosCliente> dadosClienteResponse = clienteResourceClient.dadosDoCliente(cpf);

        return SituacaoCliente
                .builder()
                .cliente(dadosClienteResponse.getBody())
                .build();
    }

}
