package Trouve_moi.app.cadastro.User.service;

import static java.lang.String.format;
import java.util.List;
import static java.util.Objects.requireNonNull;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Service;
import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;
import org.springframework.transaction.annotation.Transactional;

import Trouve_moi.app.cadastro.User.domain.User;
import Trouve_moi.app.cadastro.User.domain.cmd.AtualizarUser;
import Trouve_moi.app.cadastro.User.domain.cmd.CadastrarUser;
import Trouve_moi.app.cadastro.User.repository.UserRepository;
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
    public UUID handle(@NonNull CadastrarUser cmd) {
        User user = User.builder()
                .nome(cmd.getNome())
                .dataDeNascimento(cmd.getDataDeNascimento())
                .email(cmd.getEmail())
                .telefone(cmd.getTelefone())
                .cpf(cmd.getCpf())
                .endereco(cmd.getEndereco())
                .build();
        repository.save(user);
        return user.getIdUser();
    }

    public User handle(@NonNull AtualizarUser cmd) {
        User user = repository.findById(requireNonNull(cmd.getIdUser()))
                .orElseThrow(
                        () -> new EntityNotFoundException(
                                format("Não foi encontrado o seguinte Id: %s.",
                                        cmd.getIdUser().toString())));

        user.atualizar()
                .nome(cmd.getNome())
                .dataDeNascimento(cmd.getDataDeNascimento())
                .email(cmd.getEmail())
                .telefone(cmd.getTelefone())
                .cpf(cmd.getCpf())
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
}
