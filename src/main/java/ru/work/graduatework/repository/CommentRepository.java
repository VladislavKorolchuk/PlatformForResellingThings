package ru.work.graduatework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.work.graduatework.Entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

}
