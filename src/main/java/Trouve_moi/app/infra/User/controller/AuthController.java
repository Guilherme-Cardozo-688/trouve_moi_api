package Trouve_moi.app.cadastro.User.controller;

import Trouve_moi.app.cadastro.User.domain.User;
import Trouve_moi.app.cadastro.User.infra.dto.AuthRequest;
import Trouve_moi.app.cadastro.User.infra.dto.AuthResponse;
import Trouve_moi.app.cadastro.User.infra.security.JwtUtil;
import Trouve_moi.app.cadastro.User.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getSenha())
            );
        } catch (Exception e) {
            throw new Exception("Email ou senha incorretos", e);
        }

        final User user = userService.findByEmail(authRequest.getEmail());
        final String jwt = jwtUtil.generateToken(user);

        return ResponseEntity.ok(new AuthResponse(jwt));
    }
} 