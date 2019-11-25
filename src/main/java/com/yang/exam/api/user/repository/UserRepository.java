package com.yang.exam.api.user.repository;

import com.yang.exam.api.user.model.User;
import com.yang.exam.commons.reposiotry.BaseRepository;

public interface UserRepository extends BaseRepository<User, Integer> {

    User findByUserName(String userName);

    User findByEmail(String email);

    User findByMobile(String mobile);

}
