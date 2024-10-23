package Trouve_moi.app.cadastro.User.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import io.micrometer.common.lang.NonNull;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
@Builder
public class User {
    @Id
    private UUID idUser;

    @ManyToMany
    private List<User> users;

    @NonNull
    private String nome;

    @NonNull
    private LocalDate dataDeNascimento;

    @NonNull
    @Email
    private String email;

    @NonNull
    @Pattern(regexp = "^\\(?[1-9]{2}\\)? ?(?:[2-8]|9[0-9])[0-9]{3}\\-?[0-9]{4}$")
    private String telefone;

    @NonNull
    @Pattern(regexp = "\\d{3}\\.?\\d{3}\\.?\\d{3}\\-?\\d{2}")
    private String cpf;

    @NonNull
    @Embedded
    private Endereco endereco;

    public User(UserBuilder builder) {
        this.users = null;
        this.nome = builder.nome;
        this.dataDeNascimento = builder.dataDeNascimento;
        this.email = builder.email;
        this.telefone = builder.telefone;
        this.cpf = builder.cpf;
        this.endereco = builder.endereco;
    }

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

    public static class UserBuilder {
        private UUID idUser;

        public User build() {
            idUser = UUID.randomUUID();

            return new User(this);
        }
    }

}
