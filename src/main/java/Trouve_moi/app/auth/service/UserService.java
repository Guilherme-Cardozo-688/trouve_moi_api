package Trouve_moi.app.auth.service;

import static java.lang.String.format;
import java.util.List;
import static java.util.Objects.requireNonNull;
import java.util.Optional;
import java.util.UUID;

import Trouve_moi.app.auth.domain.User;
import Trouve_moi.app.auth.repository.UserRepository;
import Trouve_moi.app.auth.service.cmd.AtualizarUser;
import Trouve_moi.app.auth.service.cmd.CadastrarUser;
import Trouve_moi.app.value_objects.Cpf;
import Trouve_moi.app.value_objects.Email;
import Trouve_moi.app.value_objects.Telefone;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Service;
import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;
import org.springframework.transaction.annotation.Transactional;

import io.micrometer.common.lang.NonNull;
import jakarta.persistence.EntityNotFoundException;
import static jakarta.persistence.LockModeType.PESSIMISTIC_READ;
import lombok.AllArgsConstructor;

@Service
@Transactional(propagation = REQUIRES_NEW)
@AllArgsConstructor
public class UserService {
    private UserRepository repository;

    @NonNull
    @Lock(PESSIMISTIC_READ)
    public UUID handle(@NonNull CadastrarUser cmd) throws Exception {
        try {
            Optional<User> userExistente = repository.findByEmail(cmd.getEmail());
            if (userExistente.isPresent()) {
                throw new IllegalArgumentException(
                        format("Já existe um usuário cadastrado com o email: %s", cmd.getEmail()));
            }
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Erro ao verificar email: " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("Erro inesperado ao verificar email: " + e.getMessage());
        }

        if (repository.findByCpf(cmd.getCpf()).isPresent()) {
            throw new IllegalArgumentException(
                    format("Já existe um usuário cadastrado com o CPF: %s", cmd.getCpf()));
        }

        User user = User.builder()
                .nome(cmd.getNome())
                .dataDeNascimento(cmd.getDataDeNascimento())
                .email(Email.of(cmd.getEmail()))
                .telefone(Telefone.of(cmd.getTelefone()))
                .cpf(Cpf.of(cmd.getCpf()))
                .endereco(cmd.getEndereco())
                .senha(cmd.getSenha())
                .build();
        repository.save(user);
        return user.getIdUser();
    }

    public User handle(@NonNull AtualizarUser cmd) throws Exception {
        User user = repository.findById(requireNonNull(cmd.getIdUser()))
                .orElseThrow(
                        () -> new EntityNotFoundException(
                                format("Não foi encontrado o seguinte Id: %s.",
                                        cmd.getIdUser().toString())));

        user.atualizar()
                .nome(cmd.getNome())
                .dataDeNascimento(cmd.getDataDeNascimento())
                .email(Email.of(cmd.getEmail()))
                .telefone(Telefone.of(cmd.getTelefone()))
                .cpf(Cpf.of(cmd.getCpf()))
                .endereco(cmd.getEndereco())
                .aplicar();
        return repository.save(user);
    }

    public Optional<User> buscarPorIdUser(@NonNull UUID idUser) {
        return repository.findById(idUser);
    }

    public List<User> listarTodos() {
        return repository.findAll();
    }

    public void deletar(@NonNull UUID idUser) {
        repository.deleteById(idUser);
    }

    public User autenticar(String email, String senha) {
        User user = repository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException(
                        format("Usuário não encontrado com o email: %s", email)));

        if (!user.verificarSenha(senha)) {
            throw new IllegalArgumentException("Senha incorreta");
        }

        return user;
    }

    public User addUser(UUID idUser, UUID userId) {
        User user = repository.findById(idUser)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Usuário não encontrado com o Id: %s", idUser.toString())));
        User userToAdd = repository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Usuário não encontrado com o Id: %s", userId.toString())));

        if (user.getUsers().contains(userToAdd)) {
            throw new IllegalArgumentException("Usuário já está na lista");
        }

        user.addUser(userToAdd);
        return repository.save(user);
    }
}
