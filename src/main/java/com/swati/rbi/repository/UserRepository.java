package com.swati.rbi.repository;

import com.swati.rbi.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByNameAndEmail(String name, String email);

}
