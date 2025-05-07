package Trouve_moi.app.value_objects;

import jakarta.persistence.Embeddable;
import lombok.*;

import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PUBLIC;

@Getter
@ToString
@Embeddable
@EqualsAndHashCode(of = "cpf")
public class Cpf{
    public static Cpf EMPTY = new Cpf("");
    private String cpf;

    public static Cpf of(String numero) throws Exception {
        if (numero == null || numero.isEmpty()) {
            throw new Exception("O seu CPF não pode ser nulo e nem 0");
        }
        return new Cpf(numero);
    }

}
