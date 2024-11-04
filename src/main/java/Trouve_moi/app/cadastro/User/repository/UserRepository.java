package Trouve_moi.app.cadastro.User.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import Trouve_moi.app.cadastro.User.domain.User;

public interface UserRepository extends JpaRepository<User, UUID> {

}