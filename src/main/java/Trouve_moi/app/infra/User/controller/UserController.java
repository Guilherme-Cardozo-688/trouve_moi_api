package Trouve_moi.app.cadastro.User.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import lombok.AllArgsConstructor;
import Trouve_moi.app.cadastro.User.domain.User;
import Trouve_moi.app.cadastro.User.domain.cmd.CadastrarUser;
import Trouve_moi.app.cadastro.User.infra.AutenticacaoRequest;
import Trouve_moi.app.cadastro.User.service.UserService;

import java.util.List;
import java.util.UUID;

import Trouve_moi.app.cadastro.User.domain.cmd.AtualizarUser;
import io.micrometer.common.lang.NonNull;
import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {
    private final UserService service;

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<UUID> cadastrar(@RequestBody CadastrarUser cmd) {
        UUID id = service.handle(cmd);
        return ResponseEntity.ok(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> alterar(@PathVariable @NonNull UUID id, @RequestBody AtualizarUser cmd) {

        cmd.setIdUser(id);

        User salvar = service.handle(cmd);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvar);
    }

    @PutMapping("addUser/{idUser}/{userId}")
    public ResponseEntity<User> alterar(@PathVariable @NonNull UUID idUser, @PathVariable @NonNull UUID userId) {
        User salvar = service.addUser(idUser, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvar);
    }

    @GetMapping
    public ResponseEntity<List<User>> listarTodos() {
        List<User> users = service.listarTodos();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> buscarPorId(@PathVariable @NonNull UUID id) {
        return service.buscarPorIdUser(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable @NonNull UUID id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/autenticar")
    public ResponseEntity<User> autenticar(@RequestBody AutenticacaoRequest request) {
        try {
            User user = service.autenticar(request.getEmail(), request.getSenha());
            return ResponseEntity.ok(user);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
