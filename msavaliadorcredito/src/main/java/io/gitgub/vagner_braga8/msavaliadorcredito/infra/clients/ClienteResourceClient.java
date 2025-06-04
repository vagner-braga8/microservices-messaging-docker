package io.gitgub.vagner_braga8.msavaliadorcredito.infra.clients;

import io.gitgub.vagner_braga8.msavaliadorcredito.domain.model.DadosCliente;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "msclientes", path = "/clientes")
public interface ClienteResourceClient {

    /**
     * Cliente Feign utilizado para comunicação síncrona com o microsserviço msclientes.
     * <p>
     * O método {@code dadosDoCliente} realiza uma requisição GET ao endpoint <code>/clientes</code>,
     * com o parâmetro de consulta {@code cpf}.
     * <p>
     * Esta chamada corresponde ao seguinte método no msclientes:
     * <pre>
     *     @GetMapping(params = "cpf")
     *     public ResponseEntity&lt;DadosCliente&gt; dadosDoCliente(@RequestParam("cpf") String cpf)
     * </pre>
     * O OpenFeign abstrai a chamada HTTP, permitindo que o serviço atual consulte os dados de um cliente
     * diretamente através da interface, sem precisar lidar com detalhes de HTTP (como RestTemplate ou WebClient).
     */
    @GetMapping(params = "cpf")
    ResponseEntity<DadosCliente> dadosDoCliente(@RequestParam("cpf") String cpf);
}
