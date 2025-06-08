package io.gitgub.vagner_braga8.msavaliadorcredito.application;

import feign.FeignException;
import io.gitgub.vagner_braga8.msavaliadorcredito.application.ex.DadosClienteNotFoundExcepetion;
import io.gitgub.vagner_braga8.msavaliadorcredito.application.ex.ErroComunicacaoMicroservicesException;
import io.gitgub.vagner_braga8.msavaliadorcredito.domain.model.CartaoCliente;
import io.gitgub.vagner_braga8.msavaliadorcredito.domain.model.DadosCliente;
import io.gitgub.vagner_braga8.msavaliadorcredito.domain.model.SituacaoCliente;
import io.gitgub.vagner_braga8.msavaliadorcredito.infra.clients.CartoesResourceClient;
import io.gitgub.vagner_braga8.msavaliadorcredito.infra.clients.ClienteResourceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AvaliadorCreditoService {

    private final ClienteResourceClient clienteResourceClient;
    private final CartoesResourceClient cartoesResourceClient;

    public SituacaoCliente obterSituacaoCliente(String cpf) throws DadosClienteNotFoundExcepetion, ErroComunicacaoMicroservicesException {
        try {
            ResponseEntity<DadosCliente> dadosClienteResponse = clienteResourceClient.dadosDoCliente(cpf);
            ResponseEntity<List<CartaoCliente>> cartoesResponse = cartoesResourceClient.getCartoesByCpf(cpf);

            return SituacaoCliente
                    .builder()
                    .cliente(dadosClienteResponse.getBody())
                    .cartoes(cartoesResponse.getBody())
                    .build();
        } catch (FeignException.FeignClientException e) {
            int status = e.status();
            if (HttpStatus.NOT_FOUND.value() == status) {
                throw new DadosClienteNotFoundExcepetion();
            }
            throw new ErroComunicacaoMicroservicesException(e.getMessage(), status);
        }
    }

}
