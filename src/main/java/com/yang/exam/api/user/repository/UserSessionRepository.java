package com.yang.exam.api.user.repository;


import com.yang.exam.api.user.entity.UserSession;
import com.yang.exam.commons.reposiotry.BaseRepository;

public interface UserSessionRepository extends BaseRepository<UserSession, Integer> {

    UserSession findByToken(String token);
}
