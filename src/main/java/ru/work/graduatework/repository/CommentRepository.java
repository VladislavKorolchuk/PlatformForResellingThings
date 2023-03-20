package ru.work.graduatework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.work.graduatework.Entity.Comment;
import ru.work.graduatework.dto.AdsCommentDto;
import ru.work.graduatework.dto.ResponseWrapperCommentDto;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    void deleteAdsCommentsByAdId(long id);

    Optional <Comment> findByIdAndAdId(long id, long adsId);

    Collection<Comment> findAllByAdId (long id);

}
