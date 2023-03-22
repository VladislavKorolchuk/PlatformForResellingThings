package ru.work.graduatework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.work.graduatework.Entity.Ads;

import java.util.Collection;
import java.util.List;

@Repository
public interface AdRepository extends JpaRepository<Ads, Integer> {

    List<Ads> findByTitleIgnoreCase(String title);

    Collection<Ads> findAllByAuthorId(long id);

}
