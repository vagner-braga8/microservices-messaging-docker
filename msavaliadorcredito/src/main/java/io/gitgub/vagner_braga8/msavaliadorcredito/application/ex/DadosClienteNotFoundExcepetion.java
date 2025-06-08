package io.gitgub.vagner_braga8.msavaliadorcredito.application.ex;

public class DadosClienteNotFoundExcepetion extends Exception {
    public DadosClienteNotFoundExcepetion() {
        super("Dados do cliente não encontrado para o CPF informado.");
    }
}
