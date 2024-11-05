package Trouve_moi.app.cadastro.User.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.UUID;

public class UserTest {

    private User user;
    private Endereco endereco;

    @BeforeEach
    void setUp() {
        endereco = new Endereco("Rua Teste", 123);

        user = User.builder()
                .nome("João Silva")
                .dataDeNascimento(LocalDate.of(1990, 1, 1))
                .email("joao@email.com")
                .telefone("(11)999999999")
                .cpf("123.456.789-00")
                .endereco(endereco)
                .senha("senha123")
                .build();
    }

    @Test
    void deveCriarUsuarioComSucesso() {
        assertNotNull(user);
        assertEquals("João Silva", user.getNome());
        assertEquals("joao@email.com", user.getEmail());
        assertEquals("123.456.789-00", user.getCpf());
    }

    @Test
    void deveVerificarSenhaCorretamente() {
        assertTrue(user.verificarSenha("senha123"));
        assertFalse(user.verificarSenha("senhaErrada"));
    }

    @Test
    void deveAdicionarUsuarioNaLista() {
        User outroUser = User.builder()
                .nome("Maria Silva")
                .dataDeNascimento(LocalDate.of(1995, 5, 5))
                .email("maria@email.com")
                .telefone("(11)988888888")
                .cpf("987.654.321-00")
                .endereco(endereco)
                .senha("senha456")
                .build();

        user.addUser(outroUser);

        assertEquals(1, user.getUsers().size());
        assertTrue(user.getUsers().contains(outroUser));
    }

}
