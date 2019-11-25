package com.yang.exam.api.tag.service;

import com.yang.exam.api.tag.model.Tag;

import java.util.List;

public interface TagService {

    void save(Tag tag) throws Exception;

    void delete(Integer id) throws Exception;

    List<Tag> tag() throws Exception;
}
