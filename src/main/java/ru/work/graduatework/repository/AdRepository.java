package ru.work.graduatework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.work.graduatework.Entity.Ad;

import java.util.Collection;
import java.util.List;

@Repository
public interface AdRepository extends JpaRepository<Ad, Long> {

    Collection<Ad> findAllByAuthorId(long id);

}
