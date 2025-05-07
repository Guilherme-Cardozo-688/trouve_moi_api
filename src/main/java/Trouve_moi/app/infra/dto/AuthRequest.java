package Trouve_moi.app.cadastro.User.infra.dto;

import lombok.Data;

@Data
public class AuthRequest {
    private String email;
    private String senha;
} 