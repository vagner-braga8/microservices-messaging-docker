package io.github.vagner_braga8.mscartoes.infra.mqueue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.vagner_braga8.mscartoes.domain.Cartao;
import io.github.vagner_braga8.mscartoes.domain.ClienteCartao;
import io.github.vagner_braga8.mscartoes.domain.DadosSolicitacaoEmissaoCartao;
import io.github.vagner_braga8.mscartoes.infra.repository.CartaoRepository;
import io.github.vagner_braga8.mscartoes.infra.repository.ClienteCartaoRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class EmissaoCartaoSubscriber {

    private final CartaoRepository cartaoRepository;
    private final ClienteCartaoRepository clienteCartaoRepository;

    @RabbitListener(queues = "${mq.queues.emissao-cartoes}")
    public void receberSolicitacaoCartao(@Payload String payload) {
        log.info(" Solicitando a emissão do cartão... ");
        try {
            var mapper = new ObjectMapper();
            DadosSolicitacaoEmissaoCartao dados = mapper.readValue(payload, DadosSolicitacaoEmissaoCartao.class);

            log.info("Dados recebidos para CPF: {}", dados.getCpf());

            Cartao cartao = cartaoRepository
                    .findById(dados.getIdCartao())
                    .orElseThrow(() -> new IllegalArgumentException("ID do cartão não encontrado: " + dados.getIdCartao()));

            ClienteCartao clienteCartao = new ClienteCartao();
            clienteCartao.setCartao(cartao);
            clienteCartao.setCpf(dados.getCpf());
            clienteCartao.setLimite(dados.getLimiteLiberado());

            clienteCartaoRepository.save(clienteCartao);
            log.info("Cartão para o CPF {} salvo com sucesso!", dados.getCpf());

        } catch (JsonProcessingException e) {
            log.error("ERRO DE PARSING: Falha ao converter o payload. Payload: {}", payload, e);
        } catch (Exception e) {
            log.error("ERRO NO PROCESSAMENTO: Falha inesperada ao processar emissão de cartão. Payload: {}", payload, e);
        }
    }
}
