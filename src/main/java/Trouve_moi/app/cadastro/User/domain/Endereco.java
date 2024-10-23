package Trouve_moi.app.cadastro.User.domain;

import javax.persistence.Embeddable;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@EqualsAndHashCode
@NoArgsConstructor
public final class Endereco {
    private String logradouro;
    private Integer numero;

    public Endereco(String logradouro, Integer numero) {
        this.logradouro = logradouro;
        this.numero = numero;
    }

    public static Endereco of(String logradouro, Integer numero) throws Exception {
        if (logradouro == null || logradouro.isEmpty()) {
            throw new Exception("Logradouro não pode ser nulo e nem vazio");
        }
        if (numero == null || numero == 0) {
            throw new Exception("Numero não pode ser nulo e nem 0");
        }
        return new Endereco(logradouro, numero);
    }
}