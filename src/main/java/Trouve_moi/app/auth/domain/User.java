package Trouve_moi.app.auth.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import Trouve_moi.app.auth.domain.UserForm;
import Trouve_moi.app.value_objects.Cpf;
import Trouve_moi.app.value_objects.Telefone;
import Trouve_moi.app.value_objects.Email;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Embedded;

import io.micrometer.common.lang.NonNull;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "users")
@NoArgsConstructor
@Getter
@Builder
@AllArgsConstructor
public class User implements UserDetails {
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
    private Email email;

    @NonNull
    private Telefone telefone;

    @NonNull
    private Cpf cpf;

    @NonNull
    @Embedded
    private Trouve_moi.app.cadastro.User.domain.Endereco endereco;

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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
