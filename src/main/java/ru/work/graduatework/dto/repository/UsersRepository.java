package ru.work.graduatework.dto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.work.graduatework.Entity.Users;
import java.util.Optional;
@Repository
public interface UsersRepository extends JpaRepository<Users, Integer> {

   Optional<Users> findByEmail(String email);

}
