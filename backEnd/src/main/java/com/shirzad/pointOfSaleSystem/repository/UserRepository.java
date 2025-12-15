package com.shirzad.pointOfSaleSystem.repository;

import com.shirzad.pointOfSaleSystem.modal.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);
}
