package Trouve_moi.app.cadastro.User.domain;

import java.time.LocalDate;
import java.util.function.Consumer;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserForm {
    private final Consumer<UserForm> action;
    private User users;
    private String nome;
    private LocalDate dataDeNascimento;
    private String email;
    private String telefone;
    private String cpf;
    private Endereco endereco;

    public UserForm users(User user) {
        this.users.addUser(user);
        return this;
    }

    public UserForm nome(String nome) {
        this.nome = nome;
        return this;
    }

    public UserForm dataDeNascimento(LocalDate dataDeNascimento) {
        this.dataDeNascimento = dataDeNascimento;
        return this;
    }

    public UserForm email(String email) {
        this.email = email;
        return this;
    }

    public UserForm telefone(String telefone) {
        this.telefone = telefone;
        return this;
    }

    public UserForm cpf(String cpf) {
        this.cpf = cpf;
        return this;
    }

    public UserForm endereco(Endereco endereco) {
        this.endereco = endereco;
        return this;
    }

    public void aplicar() {
        action.accept(this);
    }
}