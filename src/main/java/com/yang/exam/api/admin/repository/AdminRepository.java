package com.yang.exam.api.admin.repository;


import com.yang.exam.api.admin.model.Admin;
import com.yang.exam.commons.reposiotry.BaseRepository;

public interface AdminRepository extends BaseRepository<Admin, Integer> {

    Admin findByUserName(String userName);
}
