package ru.work.graduatework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.work.graduatework.Entity.Ads;

public interface AdsRepository extends JpaRepository<Ads, Integer> {
}
