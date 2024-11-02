package org.example.taskmanager.repositories;

import org.example.taskmanager.models.Task;
import org.example.taskmanager.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);


    User findByUsername(String username);
}
