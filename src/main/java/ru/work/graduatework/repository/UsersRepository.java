package ru.work.graduatework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.work.graduatework.Entity.Users;

public interface UsersRepository extends JpaRepository<Users, Integer> {
}
