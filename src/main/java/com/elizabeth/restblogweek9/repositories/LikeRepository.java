package com.elizabeth.restblogweek9.repositories;

import com.elizabeth.restblogweek9.model.Likes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface LikeRepository extends JpaRepository<Likes, Integer> {
    @Query(value = "select * from Likes where user_id=?1 and post_id =?2 ", nativeQuery = true)
    Likes findLikeByUser_idAndPost_id(int user_id, int post_id);

    @Query(value = "select * from Likes  where post_id =?1 ", nativeQuery = true)
    List<Likes> likesList(int post_id);
}
