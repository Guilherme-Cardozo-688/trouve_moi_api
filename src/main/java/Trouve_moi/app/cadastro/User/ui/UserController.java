package Trouve_moi.app.cadastro.User.ui;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;

import lombok.AllArgsConstructor;
import Trouve_moi.app.cadastro.User.domain.User;
import Trouve_moi.app.cadastro.User.domain.cmd.CadastrarUser;
import Trouve_moi.app.cadastro.User.service.UserService;

import java.util.List;
import java.util.UUID;

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

    @GetMapping
    public ResponseEntity<List<User>> listarTodos() {
        List<User> users = service.listarTodos();
        return ResponseEntity.ok(users);
    }
}
