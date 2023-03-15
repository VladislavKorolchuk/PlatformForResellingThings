package ru.work.graduatework.dto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.work.graduatework.Entity.Ads;
@Repository
public interface AdsRepository extends JpaRepository<Ads, Integer> {
}
