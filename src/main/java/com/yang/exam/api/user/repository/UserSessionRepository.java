package com.yang.exam.api.user.repository;


import com.yang.exam.api.user.entity.UserSession;
import com.yang.exam.commons.reposiotry.BaseRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserSessionRepository extends BaseRepository<UserSession, Integer> {

    UserSession findByToken(String token);

    @Query(value = "select * from user_session ORDER BY user_id=:id DESC LIMIT 1", nativeQuery = true)
    UserSession findByUserId(Integer id);

}
