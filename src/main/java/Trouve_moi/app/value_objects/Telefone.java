package Trouve_moi.app.value_objects;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.util.regex.Matcher;


@Getter
@ToString
@Embeddable
@EqualsAndHashCode(of = "telefone")
@NoArgsConstructor(access = AccessLevel.PUBLIC, force = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public final class Telefone{
    
    public static Telefone EMPTY = new Telefone();

    private String telefone;

    public static Telefone of(String numero) throws Exception {
        if (numero == null || numero.isEmpty()) {
            throw new Exception("Numero não pode ser nulo e nem 0");
        }
        return new Telefone(numero);
    }
}