package ru.java.crm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.java.crm.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
