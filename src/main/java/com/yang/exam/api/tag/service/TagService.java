package com.yang.exam.api.tag.service;

import com.yang.exam.api.tag.model.Tag;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface TagService {

    Tag findById(Integer id) throws Exception;

    Tag getById(Integer id) throws Exception;

    void save(Tag tag) throws Exception;

    void delete(Integer id) throws Exception;

    List<Tag> findTags() throws Exception;

    Map<Integer, Tag> findTagByIds(Collection<Integer> ids) throws Exception;
}
