package Trouve_moi.app.cadastro.User.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Embedded;

import io.micrometer.common.lang.NonNull;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "users")
@NoArgsConstructor
@Getter
@Builder
@AllArgsConstructor
public class User {
    @Id
    private UUID idUser;

    @Builder.Default
    @ManyToMany
    private List<User> users = new ArrayList<>();

    @NonNull
    private String nome;

    @NonNull
    private LocalDate dataDeNascimento;

    @NonNull
    @Email
    private String email;

    @NonNull
    @Pattern(regexp = "^\\(?[1-9]{2}\\)? ?(?:[2-8]|9[0-9])[0-9]{3}\\-?[0-9]{4}$", message = "O telefone Deve estar indentado de maneira correta.")
    private String telefone;

    @NonNull
    @Pattern(regexp = "\\d{3}\\.?\\d{3}\\.?\\d{3}\\-?\\d{2}", message = "O cpf deve ter 11 números")
    private String cpf;

    @NonNull
    @Embedded
    private Endereco endereco;

    @NonNull
    private String senha;

    public void addUser(User user) {
        if (users == null) {
            users = new ArrayList<>();
        }
        users.add(user);
    }

    public UserForm atualizar() {
        return new UserForm(form -> {
            if (getUsers() != null) {
                this.users = getUsers();
            }
            this.nome = form.getNome();
            this.dataDeNascimento = form.getDataDeNascimento();
            this.email = form.getEmail();
            this.telefone = form.getTelefone();
            this.cpf = form.getCpf();
            this.endereco = form.getEndereco();
        });
    }

    @PrePersist
    public void prePersist() {
        if (this.idUser == null) {
            this.idUser = UUID.randomUUID();
        }
    }

    public boolean verificarSenha(String senha) {
        return this.senha.equals(senha);
    }

}
