package ru.work.graduatework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.work.graduatework.Entity.Ads;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdsRepository extends JpaRepository<Ads, Integer> {
    List<Ads> findByTitleIgnoreCase(String title);
}
