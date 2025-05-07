package Trouve_moi.app.auth.domain;

import java.time.LocalDate;
import java.util.function.Consumer;

import Trouve_moi.app.cadastro.User.domain.Endereco;
import Trouve_moi.app.value_objects.Cpf;
import Trouve_moi.app.value_objects.Email;
import Trouve_moi.app.value_objects.Telefone;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserForm {
    private final Consumer<UserForm> action;
    private User users;
    private String nome;
    private LocalDate dataDeNascimento;
    private Email email;
    private Telefone telefone;
    private Cpf cpf;
    private Trouve_moi.app.cadastro.User.domain.Endereco endereco;

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

    public UserForm email(Email email) throws Exception{
        this.email = email;
        return this;
    }

    public UserForm telefone(Telefone telefone) throws Exception{
        this.telefone = telefone;
        return this;
    }

    public UserForm cpf(Cpf cpf) throws Exception{
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