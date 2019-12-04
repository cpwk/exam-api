package com.yang.exam.api.admin.repository;


import com.yang.exam.api.admin.model.AdminSession;
import com.yang.exam.commons.reposiotry.BaseRepository;

public interface AdminSessionRepository extends BaseRepository<AdminSession, Integer> {

    AdminSession findByToken(String token);

}
