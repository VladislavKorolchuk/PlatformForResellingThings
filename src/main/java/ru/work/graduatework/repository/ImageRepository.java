package ru.work.graduatework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.work.graduatework.Entity.Image;

public interface ImageRepository extends JpaRepository<Image, Integer> {
}
