package Trouve_moi.app.cadastro.User.domain.cmd;

import java.time.LocalDate;

import Trouve_moi.app.cadastro.User.domain.Endereco;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CadastrarUser {
    private String nome;
    private LocalDate dataDeNascimento;
    private String email;
    private String telefone;
    private String cpf;
    private Endereco endereco;
}