package com.yang.exam.api.user.repository;


import com.yang.exam.api.user.model.User;
import com.yang.exam.commons.reposiotry.BaseRepository;

public interface UserRepository extends BaseRepository<User, Integer> {

    User findByUsername(String username) throws Exception;

    User findByMobile(String mobile);

    User findByEmail(String email);

}
