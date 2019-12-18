package com.yang.exam.api.tag.service;

import com.yang.exam.api.tag.entity.TagError;
import com.yang.exam.api.tag.model.Tag;
import com.yang.exam.api.tag.repository.TagRepository;
import com.yang.exam.commons.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: yangchengcheng
 * @Date: 2019-11-22 20:00
 * @Version：1.0
 */
@Service
public class TagServiceImpl implements TagService, TagError {

    @Autowired
    private TagRepository tagRepository;

    @Override
    public void save(Tag tag) throws Exception {
        if (tag.getId() == null) {
            tagRepository.save(tag);
        }
    }

    @Override
    public List<Tag> findTags() throws Exception {
        return tagRepository.findAll();
    }

    @Override
    public Tag findById(Integer id) throws Exception {
        return tagRepository.findById(id).orElse(null);
    }

    @Override
    public Tag getById(Integer id) throws Exception {
        Tag tag = findById(id);
        if (tag == null) {
            throw new ServiceException(ERR_DATA_NOT_FOUND);
        }
        return tag;
    }

    @Override
    public void delete(Integer id) throws Exception {
        Tag tag = findById(id);
        if (tag.getId() != null) {
            tagRepository.delete(tag);
        }
    }

    @Override
    public Map<Integer, Tag> findTagByIds(Collection<Integer> ids) throws Exception {
        List<Tag> tags = tagRepository.findAllById(ids);
        Map<Integer, Tag> map = new HashMap<>();
        for (Tag tag : tags) {
            map.put(tag.getId(), tag);
        }
        return map;
    }
}
