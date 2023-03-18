package ru.work.graduatework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.work.graduatework.Entity.Comment;
@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
}
