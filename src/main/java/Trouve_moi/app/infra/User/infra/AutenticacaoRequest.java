package Trouve_moi.app.cadastro.User.infra;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AutenticacaoRequest {
    private String email;
    private String senha;
}