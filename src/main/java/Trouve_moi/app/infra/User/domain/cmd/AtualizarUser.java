package Trouve_moi.app.cadastro.User.domain.cmd;

import java.time.LocalDate;
import java.util.UUID;

import Trouve_moi.app.cadastro.User.domain.Endereco;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AtualizarUser {
    private UUID idUser;
    private String nome;
    private LocalDate dataDeNascimento;
    private String email;
    private String telefone;
    private String cpf;
    private Endereco endereco;
}
